package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.ExcelUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Department;
import co.bugu.tes.service.IDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    IDepartmentService departmentService;

    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    /**
    * 列表，分页显示
    * @param department  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Department department, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Department> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = departmentService.listByObject(department, pageInfo);
            model.put("pi", pageInfo);
            model.put("department", department);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "department/list";

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
            Department department = departmentService.findById(id);
            model.put("department", department);

            List<Department> departmentList = departmentService.findAllByObject(null);
            model.put("departmentList", departmentList);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "department/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param department
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Department department, ModelMap model){
        try{
            Date now = new Date();
            if(department.getId() == null){
                department.setCreateTime(now);
            }
            department.setUpdateTime(now);
            departmentService.saveOrUpdate(department);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("department", department);
            model.put("errMsg", "保存失败");
            return "department/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param department 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Department department){
        try{
            List<Department> list = departmentService.findAllByObject(department);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param department id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Department department){
        try{
            departmentService.delete(department);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
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
        String fileName = "部门信息模板";
        String path = request.getSession().getServletContext().getRealPath("file");
        File file = new File(path, "department.xlsx");
        ExcelUtil.download(file, response, fileName);
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}
