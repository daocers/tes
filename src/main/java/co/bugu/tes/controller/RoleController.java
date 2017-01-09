package co.bugu.tes.controller;

//import co.bugu.framework.core.dao.PageInfo;
//import co.bugu.framework.util.JsonUtil;
//import co.bugu.tes.global.Constant;
//import co.bugu.tes.model.Authority;
//import co.bugu.tes.model.Role;
import co.bugu.framework.dao.PageInfo;
import co.bugu.tes.domain.Authority;
import co.bugu.tes.domain.Role;
import co.bugu.tes.service.IAuthorityService;
import co.bugu.tes.service.IRoleService;
import co.bugu.tes.util.JsonUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    IRoleService roleService;
    @Autowired
    IAuthorityService authorityService;

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    /**
    * 列表，分页显示
    * @param role  查询数据
    * @param curPage 当前页码，从1开始
    * @param showCount 当前页码显示数目
    * @param model
    * @return
    */
    @RequestMapping(value = "/list")
    public String list(Role role, Integer curPage, Integer showCount, ModelMap model){
        try{
//            PageInfo<Role> pageInfo = new PageInfo<>(showCount, curPage);
//            pageInfo = roleService.findByObject(role, pageInfo);
//            model.put("pi", pageInfo);
//            model.put("role", role);
            model.put("info", "someinfo");
        }catch (Exception e){
            logger.error("获取列表失败", e);
            model.put("errMsg", "获取列表失败");
        }
        return "role/list";

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
            List<Integer> idList = new ArrayList<>();
            Role role = null;
            if(id != null){
                role = roleService.findById(id);
                List<Authority> selectedList = role.getAuthorityList();
                for(Authority auth: selectedList){
                    idList.add(auth.getId());
                }
            }


            List<Authority> authorityList = authorityService.findByObject(null);
            List<Map<String, Object>> data = new ArrayList<>();
            for(Authority auth: authorityList){
                Map<String, Object> map = new HashMap<>();
                map.put("id", auth.getId());
                map.put("pId", auth.getSuperiorId());
                map.put("name", auth.getName());
                if(idList.contains(auth.getId())){
                    map.put("checked", true);
                }
//                if(Constant.AUTH_TYPE_BOX.equals(auth.getType())){
//                    map.put("open", true);
//                }
                data.add(map);
            }
            model.put("zNode", JSON.toJSONString(data, true));
            model.put("role", role);
        }catch (Exception e){
            logger.error("获取信息失败", e);
            model.put("errMsg", "获取信息失败");
        }
        return "role/edit";
    }

    /**
    * 保存结果，根据是否带有id来表示更新或者新增
    * @param role
    * @param model
    * @return
    */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Role role, @RequestParam(value = "nodeInfo", required = false) String info, ModelMap model){
        try{
//            role.setStatus(Constant.STATUS_ENABLE);
            List<Map<String, Integer>> xList = new ArrayList<>();
            if(info != null && !info.equals("")){
                JSONArray arr = JSON.parseArray(info);
                for(int i = 0; i < arr.size(); i++){
                    JSONObject obj = (JSONObject) arr.get(i);
                    Integer id = obj.getInteger("id");
                    Map<String, Integer> map = new HashMap<>();
                    map.put("authId", id);
                    xList.add(map);
                }
            }
//            roleService.save(role, xList);
        }catch (Exception e){
            logger.error("保存失败", e);
            model.put("role", role);
            model.put("zNode", info);
            model.put("errMsg", "保存失败");
            return "role/edit";
        }
        return "redirect:list.do";
    }

    /**
    * 异步请求 获取全部
    * @param role 查询条件
    * @return
    */
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String listAll(Role role){
        try{
            List<Role> list = roleService.findByObject(role);
            return JsonUtil.toJsonString(list);
        }catch (Exception e){
            logger.error("获取全部列表失败", e);
            return "-1";
        }
    }

    /**
    * 异步请求 删除
    * @param role id
    * @return
    */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete(Role role){
        try{
            roleService.delete(role);
            return "0";
        }catch (Exception e){
            logger.error("删除失败", e);
            return "-1";
        }
    }
}
