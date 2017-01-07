package co.bugu.tes.controller;

import co.bugu.framework.dao.BaseDao;
import co.bugu.framework.dao.ThreadLocalUtil;
import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/28.
 */
@Controller
@RequestMapping("/v1/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService<User> userService;

    @Autowired
    BaseDao baseDao;


    @RequestMapping("/index")
    public String toLogin(ModelMap modelMap){
        try{
//            Map<String, Object> map = new HashMap<>();
//            map.put("name", "allen");
//            ThreadLocalUtil.set(map);
            User user = new User();
            user.setId(1);
            user.setUsername("allen");
            user.setPassword("allen");
            List<User> list  = userService.findByObject(user);
            user.setId(2);
            userService.save(user);
            logger.debug("user: {}" , user);
            logger.debug("userList: {}", list);

            modelMap.put("msg", "something to show");

        }catch (Exception e){
            logger.error("错误：", e);
        }
        return "login";
    }


    @RequestMapping("/login")
    @ResponseBody
    public String login(User user){
//        return userService.login(user) + "";
        return "success";
    }

}
