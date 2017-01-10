package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Scene;
import co.bugu.tes.service.IPaperService;
import co.bugu.tes.service.ISceneService;
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
@RequestMapping("/scene")
public class SceneController {
    @Autowired
    ISceneService sceneService;

    @Autowired
    IPaperService paperService;

    private static Logger logger = LoggerFactory.getLogger(SceneController.class);

    /**
    * 列表，分页显示
    * @param scene  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Scene scene, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Scene> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = sceneService.listByObject(scene, pageInfo);
            model.put("pi", pageInfo);
            model.put("scene", scene);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "scene/list";

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
            Scene scene = sceneService.findById(id);
            model.put("scene", scene);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "scene/edit";
    }

    /**
     * 保存设置，跳转到选择用户界面
     * @param scene
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectUser", method = RequestMethod.POST)
    public String saveAndToSelectUser(Scene scene, ModelMap model){
        try{
            sceneService.saveOrUpdate(scene);

        }catch (Exception e){
            logger.error("保存信息失败", e);
            model.put("errMsg", "保存信息失败");
        }
        return "scene/selectUser";
    }

    /**
     * 保存本场考试用户信息，并生成试卷，然后跳转到预览页面
     * @param scene
     * @param model
     * @return
     */
    @RequestMapping(value = "/generatePaper", method = RequestMethod.POST)
    public String saveUserAndGeneratePaper(Scene scene, ModelMap model){
        try{

            paperService.generateAllPaper(scene);
        }catch (Exception e){
            logger.error("保存用户，生成试卷失败", e);
            model.put("errMsg", "保存信息失败");
        }
        return "scene/preview";
    }

    /**
     * 确认开场
     * @param scene
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public String confirm(Scene scene){
        try{
            scene.setStatus(Constant.STATUS_ENABLE);
            sceneService.updateById(scene);
            logger.info("开场成功");
        }catch (Exception e){
            logger.error("确认开场失败", e);
        }
        return "redirect:list.do";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param scene
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Scene scene, ModelMap model){
        try{
            sceneService.saveOrUpdate(scene);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("scene", scene);
            model.put("errMsg", "保存失败");
            return "scene/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param scene 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Scene scene){
        try{
            List<Scene> list = sceneService.findAllByObject(scene);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param scene id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Scene scene){
        try{
            sceneService.delete(scene);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
