package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.ExcelUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Branch;
import co.bugu.tes.service.IBranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/branchController/v1")
@RequestMapping("/branch")
public class BranchController {
    @Autowired
    IBranchService branchService;

    private static Logger logger = LoggerFactory.getLogger(BranchController.class);

    /**
    * 列表，分页显示
    * @param branch  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, Branch branch, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Branch> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = branchService.listByObject(branch, pageInfo);
            model.put("pi", pageInfo);
            model.put("branch", branch);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "branch/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param id 查询数据，一般查找id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id, ModelMap model){
        try{
            Branch branch = branchService.findById(id);
            model.put("branch", branch);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "branch/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param branch
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Branch branch, ModelMap model){
        try{
            branchService.saveOrUpdate(branch);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("branch", branch);
            model.put("errMsg", "保存失败");
            return "branch/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param branch 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Branch branch){
        try{
            List<Branch> list = branchService.findAllByObject(branch);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param branch id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Branch branch){
        try{
            branchService.delete(branch);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.GET)
    public String batchAdd(){
        return "branch/batchAdd";
    }

    @RequestMapping(value = "/batchAdd", method = RequestMethod.POST)
    public String upload(MultipartFile file, ModelMap modelMap, HttpServletRequest request){
        try{
            //导入之前需要先删除数据
            branchService.deleteAll();

            String path = request.getSession().getServletContext().getRealPath("tmp");
            Date date = new Date();
            File tarFile = new File(path, date.getTime() + file.getOriginalFilename());
            tarFile.mkdirs();
            file.transferTo(tarFile);

            Map<String, Integer> codeIdInfo = new HashMap();
            List<List<String>> data = ExcelUtil.getData(tarFile.getAbsolutePath());
            tarFile.delete();//用完删除
            data.remove(0);

//            保存基本信息
            for(List<String> line: data){
                String name = line.get(0);
                String code = line.get(1);
                String address = line.get(2);
                Date now = new Date();
                Branch branch = new Branch();
                branch.setAddress(address);
                branch.setCreateTime(now);
                branch.setCode(code);
                branch.setName(name);
                branch.setUpdateTime(now);
                branch.setLevel(Constant.BRANCH_LEVEL_TOP);
                branch.setStatus(Constant.STATUS_ENABLE);
                branchService.save(branch);

                codeIdInfo.put(code, branch.getId());
            }

            //根据数据处理level信息
            Map<String, Integer> codeLevelInfo = getCodeLevelInfo(data);

//            保存级联关系
            for(List<String> line : data){
                String code = line.get(1);
                String superiorCode = line.get(3);
                Branch branch = new Branch();
                branch.setCode(code);
                branch.setId(codeIdInfo.get(code));
                branch.setLevel(codeLevelInfo.get(code));
                branch.setSuperiorId(codeIdInfo.get(superiorCode));
                branchService.updateById(branch);

            }
        }catch (Exception e){
            logger.error("解析文件失败", e);
            modelMap.put("errMsg", "解析文件失败");
            return "branch/batchAdd";
        }
        return "redirect:list.do";
    }

    /**
     * 下载模板文件
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/downModel")
    public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = "机构信息模板";
        String path = request.getSession().getServletContext().getRealPath("file");
        File file = new File(path, "branch.xlsx");
        ExcelUtil.download(file, response, fileName);
        return null;
    }

    /**
     * 根据解析的excel获取对应的level
     * @param data
     * @return
     */
    private Map<String, Integer> getCodeLevelInfo(List<List<String>> data){
        boolean flag = true;
        Map<String, Integer> codeLevelInfo = new HashMap<>();
        while(flag){
            flag = false;
            for(List<String> line: data){
                String code = line.get(1);
                String superiorCode = line.get(3);

//                该code已经处理
                if(codeLevelInfo.containsKey(code)){
                    continue;
                }

                if(superiorCode.equals("")){
                    codeLevelInfo.put(code, Constant.BRANCH_LEVEL_TOP);
                    flag = true;
                }

                if(codeLevelInfo.containsKey(superiorCode)){
                    codeLevelInfo.put(code, codeLevelInfo.get(superiorCode) + 1);
                    flag = true;
                }
            }
        }
        return codeLevelInfo;
    }
}
