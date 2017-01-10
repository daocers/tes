package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.model.Profile;
import co.bugu.tes.service.IProfileService;
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
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    IProfileService profileService;

    private static Logger logger = LoggerFactory.getLogger(ProfileController.class);

    /**
    * 列表，分页显示
    * @param profile  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Profile profile, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Profile> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = profileService.listByObject(profile, pageInfo);
            model.put("pi", pageInfo);
            model.put("profile", profile);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "profile/list";

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
            Profile profile = profileService.findById(id);
            model.put("profile", profile);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "profile/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param profile
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Profile profile, ModelMap model){
        try{
            profileService.saveOrUpdate(profile);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("profile", profile);
            model.put("errMsg", "保存失败");
            return "profile/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param profile 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Profile profile){
        try{
            List<Profile> list = profileService.findAllByObject(profile);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param profile id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Profile profile){
        try{
            profileService.delete(profile);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
