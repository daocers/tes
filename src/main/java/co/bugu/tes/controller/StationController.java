package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Branch;
import co.bugu.tes.model.Department;
import co.bugu.tes.model.Station;
import co.bugu.tes.service.IBranchService;
import co.bugu.tes.service.IDepartmentService;
import co.bugu.tes.service.IStationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/station")
public class StationController {
    @Autowired
    IStationService stationService;
    @Autowired
    IDepartmentService departmentService;
    @Autowired
    IBranchService branchService;

    private static Logger logger = LoggerFactory.getLogger(StationController.class);

    /**
    * 列表，分页显示
    * @param station  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Station station, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Station> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = stationService.listByObject(station, pageInfo);
            model.put("pi", pageInfo);
            model.put("station", station);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "station/list";

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
            Station station = stationService.findById(id);
            model.put("station", station);
            List<Department> departmentList = departmentService.findAllByObject(null);
            model.put("departmentList", departmentList);
            List<Branch> branchList = branchService.findAllByObject(null);
            model.put("branchList", branchList);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "station/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param station
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Station station, ModelMap model){
        try{
            Date now = new Date();
            if(station.getId() == null){
                station.setCreateTime(now);
            }
            station.setUpdateTime(now);
            stationService.saveOrUpdate(station);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("station", station);
            model.put("errMsg", "保存失败");
            return "station/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param station 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Station station){
        try{
            List<Station> list = stationService.findAllByObject(station);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param station id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Station station){
        try{
            stationService.delete(station);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
