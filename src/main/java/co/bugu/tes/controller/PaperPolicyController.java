package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.framework.util.JedisUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.PaperPolicy;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.model.QuestionPolicy;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IPaperPolicyService;
import co.bugu.tes.service.IQuestionMetaInfoService;
import co.bugu.tes.service.IQuestionPolicyService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/paperpolicy")
public class PaperPolicyController {
    @Autowired
    IPaperPolicyService paperpolicyService;
    @Autowired
    IQuestionMetaInfoService questionMetaInfoService;
    @Autowired
    IQuestionPolicyService questionPolicyService;

    private static Logger logger = LoggerFactory.getLogger(PaperPolicyController.class);

    /**
    * 列表，分页显示
    * @param paperpolicy  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(PaperPolicy paperpolicy, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<PaperPolicy> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = paperpolicyService.listByObject(paperpolicy, pageInfo);
            model.put("pi", pageInfo);
            model.put("paperpolicy", paperpolicy);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "paper_policy/list";

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
            List<QuestionMetaInfo> questionMetaInfoList = questionMetaInfoService.findAllByObject(null);
            model.put("questionMetaInfoList", questionMetaInfoList);
            PaperPolicy paperpolicy = paperpolicyService.findById(id);
            if(paperpolicy == null){
                paperpolicy = new PaperPolicy();
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
                paperpolicy.setCode(format.format(new Date()));//后续需要修改为加上对应的用户部门岗位信息
            }
            model.put("paperpolicy", paperpolicy);
            List<List<QuestionPolicy>> data = new ArrayList<>();
            for(QuestionMetaInfo questionMetaInfo : questionMetaInfoList){
                QuestionPolicy questionPolicy = new QuestionPolicy();
                questionPolicy.setQuestionMetaInfoId(questionMetaInfo.getId());
                List<QuestionPolicy> questionPolicyList = questionPolicyService.findAllByObject(questionPolicy);
                if(questionMetaInfoList != null && questionPolicyList.size() > 0){
                    data.add(questionPolicyList);
                }
            }
            model.put("data", data);

        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "paper_policy/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param paperpolicy
     * @param questionMetaInfoId   题型id
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(PaperPolicy paperpolicy, int[] questionMetaInfoId, ModelMap model, HttpServletRequest request){
        try{
            Integer userId = (Integer) BuguWebUtil.get(request, Constant.SESSION_USER_ID);
            User user = JedisUtil.getJson(Constant.USER_INFO_PREFIX + userId, User.class);
            if(user != null){
                paperpolicy.setUpdateTime(new Date());
                paperpolicy.setCreateTime(new Date());
                paperpolicy.setCreateUserId(userId);
                paperpolicy.setUpdateUserId(userId);
                paperpolicy.setDepartmentId(user.getDepartmentId());
                paperpolicy.setStationId(user.getStationId());
                paperpolicy.setBranchId(user.getBranchId());
            }else{
                logger.error("获取用户信息失败， 用户id: {}", userId);
                model.put("errMsg", "用户信息异常");
                return "paper_policy/edit";
            }
            paperpolicy.setQuestionMetaInfoId(JSON.toJSONString(questionMetaInfoId));
            paperpolicyService.saveOrUpdate(paperpolicy);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("paperpolicy", paperpolicy);
            model.put("errMsg", "保存失败");
            return "paper_policy/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param paperpolicy 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(PaperPolicy paperpolicy){
        try{
            List<PaperPolicy> list = paperpolicyService.findAllByObject(paperpolicy);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param paperpolicy id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(PaperPolicy paperpolicy){
        try{
            paperpolicyService.delete(paperpolicy);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
