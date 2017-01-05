package co.bugu.tes.controller;

import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by daocers on 2017/1/5.
 */
@RequestMapping("/user")
@Controller
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    IUserService<User> userService;

    @RequestMapping("/login")
    public String login(String username, String password, HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            logger.debug("该用户已经登录");
            String path = request.getContextPath();
            int port = request.getServerPort();
            String basePath ="";
            if(port==80){
                basePath = "http://"+request.getServerName()+path+"/";
            }else{
                basePath = "http://"+request.getServerName()+":"+request.getServerPort()+path+"/";
            }
            return "redirect:"+basePath+"index.html";
        }else{
            AuthenticationToken token = new UsernamePasswordToken(username, password);

            subject.login(token);
            return "redirect:good.do";
        }
    }

    @RequestMapping("/good")
    @ResponseBody
    public String test(){
        return  "good";
    }
}
