package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Property;
import co.bugu.tes.model.PropertyItem;
import co.bugu.tes.service.IPropertyService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    IPropertyService propertyService;

    private static Logger logger = LoggerFactory.getLogger(PropertyController.class);

    /**
    * 列表，分页显示
    * @param property  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Property property, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Property> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = propertyService.listByObject(property, pageInfo);
            model.put("pi", pageInfo);
            model.put("property", property);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "property/list";

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
            Property property = propertyService.findById(id);
            model.put("property", property);
            model.put("itemInfo", JSON.toJSONString(property.getPropertyItemList(), true));
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "property/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param property
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Property property, String itemInfo, ModelMap model){
        try{
            JSONArray array = JSON.parseArray(itemInfo);
            List<PropertyItem> itemList = new ArrayList<>();
            for(int i = 0; i < array.size(); i++){
                JSONObject obj = (JSONObject) array.get(i);
                String name = obj.getString("name");
                String code = obj.getString("code");
                Integer id = obj.getInteger("id");
                PropertyItem item = new PropertyItem();
                item.setName(name);
                item.setCode(code);
                item.setId(id);
                item.setIdx(i);
                itemList.add(item);
            }
            propertyService.saveOrUpdate(property, itemList);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("property", property);
            model.put("errMsg", "保存失败");
            return "property/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param property 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Property property){
        try{
            List<Property> list = propertyService.findAllByObject(property);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param property id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Property property){
        try{
            propertyService.delete(property);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
