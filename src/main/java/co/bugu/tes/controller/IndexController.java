package co.bugu.tes.controller;

import co.bugu.framework.core.util.BuguWebUtil;
import co.bugu.framework.util.JedisUtil;
import co.bugu.tes.global.Constant;
import co.bugu.tes.model.User;
import co.bugu.tes.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by daocers on 2016/8/16.
 */
@RequestMapping("/index")
@Controller
public class IndexController {
    @Autowired
    IUserService userService;


    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/login")
    public String toLogin(HttpServletRequest request){
        if(BuguWebUtil.hasSingin(request)){
            return "redirect:/";
        }
        return "/index/login";
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public String signIn(String username, String password, HttpServletRequest request){
        try{
            User user = new User();
            user.setUsername(username);
            List<User> userList = userService.findAllByObject(user);
            if(userList.size() == 0){
                return "1";//用户不存在
            }else if(userList.size() > 1){
                return "2";//数据紊乱，应该只有一个匹配，结果有多个
            }else{
                if(password.equals(userList.get(0).getPassword())){
                    user = userService.findById(userList.get(0).getId());
                    JedisUtil.setJson(Constant.USER_INFO_PREFIX + user.getId(), user);
                    WebUtils.setSessionAttribute(request, Constant.SESSION_USER_ID, user.getId());

                    return "0";
                }else{
                    return "3";//密码不正确
                }
            }
        }catch (Exception e){
            logger.error("登录失败", e);
            return "-1";
        }
    }
}
