package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.service.IPropertyService;
import co.bugu.tes.service.IQuestionMetaInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/questionmetainfo")
public class QuestionMetaInfoController {
    @Autowired
    IQuestionMetaInfoService questionmetainfoService;

    @Autowired
    IPropertyService propertyService;

    private static Logger logger = LoggerFactory.getLogger(QuestionMetaInfoController.class);

    /**
    * 列表，分页显示
    * @param questionmetainfo  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(QuestionMetaInfo questionmetainfo, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<QuestionMetaInfo> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = questionmetainfoService.listByObject(questionmetainfo, pageInfo);
            model.put("pi", pageInfo);
            model.put("questionmetainfo", questionmetainfo);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "question_meta_info/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param id 查询数据，一般查找id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Integer id,  ModelMap model){
        try{
            if(id != null){
                QuestionMetaInfo questionmetainfo = questionmetainfoService.findById(id);
                model.put("questionmetainfo", questionmetainfo);

                List<Integer> propertyIdList = new ArrayList<>();
                for(Property property : questionmetainfo.getPropertyList()){
                    propertyIdList.add(property.getId());
                }
                model.put("propertyIdList", propertyIdList);
            }

            List<Property> propertyList = propertyService.findAllByObject(null);
            model.put("propertyList", propertyList);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }

        return "question_meta_info/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param questionmetainfo
     * @param propertyId 属性id
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(QuestionMetaInfo questionmetainfo, int[] propertyId, ModelMap model){
        try{
            List<Map<String, Integer>> list = new ArrayList<>();
            for(int i = 0; i < propertyId.length; i++){
                Integer id = propertyId[i];
                Map<String, Integer> map = new HashMap<>();
                map.put("propertyId", id);
                map.put("idx", i);
                list.add(map);
            }
            questionmetainfoService.saveOrUpdate(questionmetainfo, list);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("questionmetainfo", questionmetainfo);
            model.put("errMsg", "保存失败");
            return "question_meta_info/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param questionmetainfo 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(QuestionMetaInfo questionmetainfo){
        try{
            List<QuestionMetaInfo> list = questionmetainfoService.findAllByObject(questionmetainfo);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param questionmetainfo id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(QuestionMetaInfo questionmetainfo){
        try{
            questionmetainfoService.delete(questionmetainfo);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
