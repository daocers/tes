package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Trade;
import co.bugu.tes.service.ITradeService;
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
@RequestMapping("/trade")
public class TradeController {
    @Autowired
    ITradeService tradeService;

    private static Logger logger = LoggerFactory.getLogger(TradeController.class);

    /**
    * 列表，分页显示
    * @param trade  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Trade trade, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Trade> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = tradeService.listByObject(trade, pageInfo);
            model.put("pi", pageInfo);
            model.put("trade", trade);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "trade/list";

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
            Trade trade = tradeService.findById(id);
            model.put("trade", trade);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "trade/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param trade
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Trade trade, ModelMap model){
        try{
            tradeService.saveOrUpdate(trade);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("trade", trade);
            model.put("errMsg", "保存失败");
            return "trade/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param trade 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Trade trade){
        try{
            List<Trade> list = tradeService.findAllByObject(trade);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param trade id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Trade trade){
        try{
            tradeService.delete(trade);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
