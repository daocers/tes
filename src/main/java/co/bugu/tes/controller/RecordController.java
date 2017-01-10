package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Record;
import co.bugu.tes.service.IRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    IRecordService recordService;

    private static Logger logger = LoggerFactory.getLogger(RecordController.class);

    /**
    * 列表，分页显示
    * @param record  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Record record, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Record> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = recordService.listByObject(record, pageInfo);
            model.put("pi", pageInfo);
            model.put("record", record);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "record/list";

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
            Record record = recordService.findById(id);
            model.put("record", record);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "record/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param record
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Record record, ModelMap model){
        try{
            recordService.saveOrUpdate(record);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("record", record);
            model.put("errMsg", "保存失败");
            return "record/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param record 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Record record){
        try{
            List<Record> list = recordService.findAllByObject(record);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param record id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Record record){
        try{
            recordService.delete(record);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
