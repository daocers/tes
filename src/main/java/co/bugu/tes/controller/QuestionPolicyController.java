package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.model.QuestionMetaInfo;
import co.bugu.tes.model.QuestionPolicy;
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

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/questionpolicy")
public class QuestionPolicyController {
    @Autowired
    IQuestionPolicyService questionpolicyService;
    @Autowired
    IQuestionMetaInfoService questionMetaInfoService;

    private static Logger logger = LoggerFactory.getLogger(QuestionPolicyController.class);

    /**
    * 列表，分页显示
    * @param questionpolicy  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(QuestionPolicy questionpolicy, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<QuestionPolicy> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = questionpolicyService.listByObject(questionpolicy, pageInfo);
            model.put("pi", pageInfo);
            model.put("questionpolicy", questionpolicy);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "question_policy/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param questionPolicy 查询数据，一般查找id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(QuestionPolicy questionPolicy, ModelMap model){
        try{
            Integer id = questionPolicy.getId();
            if(id == null){//新增
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                String code = format.format(new Date());
                questionPolicy.setCode(code);
            }else{//修改
                questionPolicy = questionpolicyService.findById(id);
                String content = questionPolicy.getContent();
                if(content != null && !content.equals("")){
                    List<List<Integer>> contentList = JSON.parseObject(content, List.class);
                    model.put("contentList", contentList);
                }

                List<Property> propertyList = questionMetaInfoService
                        .findById(questionPolicy.getQuestionMetaInfoId()).getPropertyList();
                model.put("propertyList", propertyList);
            }
            model.put("questionPolicy", questionPolicy);

            List<QuestionMetaInfo> questionMetaInfoList = questionMetaInfoService.findAllByObject(null);
            model.put("questionMetaInfoList", questionMetaInfoList);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "question_policy/edit";
    }


    /**
     * 获取指定id的questionMetaInfo对应的propertyList
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProperties", method = RequestMethod.GET)
    @ResponseBody
    public String getProperties(Integer id){
        try{
            QuestionMetaInfo metaInfo = questionMetaInfoService.findById(id);
            List<Property> propertyList = metaInfo.getPropertyList();
            for(Property property: propertyList){
                property.setStatus(null);
                property.setIdx(null);
                property.setCode(null);
                property.setDescription(null);
                for(PropertyItem item: property.getPropertyItemList()){
                    item.setCode(null);
                    item.setIdx(null);
                }
            }
            return JSON.toJSONString(metaInfo.getPropertyList(), true);
        }catch (Exception e){
            logger.error("获取属性信息失败", e);
            return "-1";
        }
    }
    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param questionpolicy
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(QuestionPolicy questionpolicy, ModelMap model){
        try{
            //先处理一下重复的key
            String content = questionpolicy.getContent();
            List<int[]> list = JSON.parseArray(content, int[].class);
            Map<String, Integer> info = new HashMap<>();
            for(int[] arr: list){
                int len = arr.length;
                List<Integer> propList = new ArrayList<>();
                for(int i = 0; i < len - 1; i++){
                    propList.add(arr[i]);
                }
                String key = JSON.toJSONString(propList);
                if(info.containsKey(key)){
                    info.put(key, info.get(key) + arr[len - 1]);
                }else{
                    info.put(key, arr[len - 1]);
                }
            }

            questionpolicy.setCount(0);
            List<List<Integer>> res = new ArrayList<>();
            for(Map.Entry<String, Integer> entry: info.entrySet()){
                String key = entry.getKey();
                Integer value = entry.getValue();
                List<Integer> item = JSON.parseArray(key, Integer.class);
                item.add(value);
                res.add(item);
                questionpolicy.setCount(questionpolicy.getCount() + value);
            }
            questionpolicy.setContent(JSON.toJSONString(res));

            questionpolicy.setCreateTime(new Date());
            questionpolicy.setCreateUserId(0);
            questionpolicy.setUpdateTime(new Date());
            questionpolicy.setUpdateUserId(0);
            questionpolicyService.saveOrUpdate(questionpolicy);

        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("questionPolicy", questionpolicy);
            model.put("errMsg", "保存失败");
            return "question_policy/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param questionpolicy 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(QuestionPolicy questionpolicy){
        try{
            List<QuestionPolicy> list = questionpolicyService.findAllByObject(questionpolicy);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param questionpolicy id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(QuestionPolicy questionpolicy){
        try{
            questionpolicyService.delete(questionpolicy);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }

}
