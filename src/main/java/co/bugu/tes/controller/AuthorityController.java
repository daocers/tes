package co.bugu.tes.controller;

import co.bugu.framework.core.dao.PageInfo;
import co.bugu.framework.core.util.MvcParam;
import co.bugu.framework.core.util.ReflectUtil;
import co.bugu.framework.util.JsonUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.Authority;
import co.bugu.tes.service.IAuthorityService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    IAuthorityService authorityService;

    private static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    /**
    * 列表，分页显示
    * @param authority  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Authority authority, Integer curPage, Integer showCount, ModelMap model){
        try{
            PageInfo<Authority> pageInfo = new PageInfo<>(showCount, curPage);
            pageInfo = authorityService.listByObject(authority, pageInfo);
            model.put("pi", pageInfo);
            model.put("authority", authority);
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "authority/list";

    }

    /**
    * 查询数据后跳转到对应的编辑页面
    * @param authority 查询数据，一般查找id
    * @param model
    * @return
    */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String toEdit(Authority authority, ModelMap model){
        try{
            authority = authorityService.findById(authority.getId());
            model.put("authority", authority);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "authority/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param authority
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Authority authority, ModelMap model){
        try{
            if(authority.getId() == null){
                authorityService.save(authority);
            }else{
                authorityService.updateById(authority);
            }
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("authority", authority);
            model.put("errMsg", "保存失败");
            return "authority/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param authority 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Authority authority){
        try{
            List<Authority> list = authorityService.findAllByObject(authority);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param authority id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Authority authority){
        try{
            authorityService.delete(authority);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }

    /**
     *  初始化当前系统的所有mappingRequest，
     *  并根据数据使用情况进行处理，已经存在的，忽略，否则入库
     * @param model
     * @return
     */
    @RequestMapping(value = "/init")
    public String init(ModelMap model){
        try{
            List<Authority> authorities = authorityService.findAllByObject(null);
            List<String> urlList = new ArrayList<>();
            for(Authority auth : authorities){
                urlList.add(auth.getUrl());
            }

            List<MvcParam> list = ReflectUtil.getAnnotationInfo(AuthorityController.class.getPackage().getName());

            Set<String> controllerSet = new HashSet<>();
            for(MvcParam param: list){
                controllerSet.add(param.getControllerName());
                String url = param.getRootPath() + param.getPath();
                if(urlList.contains(url)){
                    continue;
                }
                if(param.getPath() == null){
                    continue;
                }
                Authority auth = new Authority();
                auth.setStatus(Constant.STATUS_ENABLE);
                auth.setAction(param.getMethodName());
                auth.setController(param.getControllerName());
                auth.setDescription("");
                auth.setName("");
                auth.setParam(null);
                auth.setSuperiorId(null);

                auth.setType(Constant.AUTH_TYPE_MENU);
                auth.setUrl(param.getRootPath() + param.getPath());
                auth.setAcceptMethod(param.getMethod());
                auth.setIsApi(param.getApi() ? Constant.AUTH_API_TRUE : Constant.AUTH_API_FALSE);
                authorityService.save(auth);
            }

            Authority authority = new Authority();
            authority.setType(Constant.AUTH_TYPE_BOX);
            authorities = authorityService.findAllByObject(authority);
            List<String> controllerList = new ArrayList<>();
            for(Authority auth: authorities){
                controllerList.add(auth.getController());
            }
            for(String con: controllerSet){
                if(controllerList.contains(con)){
                    continue;
                }
                Authority auth = new Authority();
                auth.setStatus(Constant.STATUS_ENABLE);
                auth.setController(con);
                auth.setType(Constant.AUTH_TYPE_BOX);
                auth.setName("");
                auth.setDescription("");
                auth.setIsApi(Constant.AUTH_API_FALSE);
                authorityService.save(auth);
                auth.setSuperiorId(auth.getId());
                authorityService.batchUpdate(auth);
            }

        }catch (Exception e){
            logger.error("初始化工程内的信息失败", e);
        }
        return "redirect:list.do";
    }

    /**
     * 通过ztree进行管理
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(ModelMap modelMap){
        try{
            Authority authority = new Authority();
            authority.setStatus(Constant.STATUS_ENABLE);
            List<Authority> list = authorityService.findAllByObject(authority);
            List<Map<String, Object>> res = new ArrayList<>();
            for(Authority auth : list){
                Map<String, Object> map = new HashedMap();
                map.put("id", auth.getId());
                map.put("pId", auth.getSuperiorId() == null ? 0 : auth.getSuperiorId());
                map.put("name", auth.getName());
                if(auth.getUrl() != null && !auth.getUrl().equals("")){//url菜单
                    map.put("dropInner", false);
                }else{//菜单目录

                }
                res.add(map);
            }
            modelMap.put("zNode", JSON.toJSONString(res));
        }catch (Exception e){
            logger.error("获取权限管理信息失败", e);
            return "redirect:list.do";
        }
        return "authority/manage";
    }

    /**
     * 批量更新ztree提交的数据
     * @param info
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(String info){
        try{
            List<Authority> authorityList = new ArrayList<>();
            JSONArray arr = JSON.parseArray(info);
            for(int i = 0; i < arr.size(); i++){
                JSONObject obj = (JSONObject) arr.get(i);
                Authority authority = null;
                Integer id = obj.getInteger("id");
                Integer superiorId = obj.getInteger("pId");
                String name = obj.getString("name");
                if(id == null){//新增的
                    authority = new Authority();
                    authority.setId(id);
                    authority.setType(Constant.AUTH_TYPE_MENU);
                    authority.setDescription("");
                }else{
                    authority = authorityService.findById(id);
                }
                authority.setIdx(i);
                authority.setName(name);
                authority.setSuperiorId(superiorId);
                authority.setStatus(Constant.STATUS_ENABLE);
                authorityList.add(authority);
            }
            authorityService.rebuildInfo(authorityList);
            return "0";
        }catch (Exception e){
            logger.error("批量更新失败", e);
            return "-1";
        }
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    @ResponseBody
    public String commit(Authority authority){
        try{
            authorityService.updateById(authority);
        }catch (Exception e){
            logger.error("表格提交失败", e);
            return "-1";
        }
        return "0";
    }
}
