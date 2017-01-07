package co.bugu.tes.controller;

import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public String login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            logger.debug("该用户已经登录");
        } else {
            AuthenticationToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
//            String preUrl = getPreUrl(request);
//            if(!preUrl.contains("login")){
//                request.getRequestDispatcher(getPreUrl(request)).forward(request, response);
//            }
        }
        return "redirect:index.do";
    }


    @RequestMapping("/logout.do")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            logger.debug("用户退出");
            subject.logout();
        }
        return "redirect:/login.do";
    }

    @RequestMapping("/index")
    @RequiresAuthentication
    public String index(){
        return "index";
    }


    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }



    @RequiresRoles("user")
    @RequestMapping("/good")
    @ResponseBody
    public String test() {
        return "good";
    }

    private String getPreUrl(HttpServletRequest request) {
        SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(request);
        if (savedRequest != null) {
            if (savedRequest.getMethod().equalsIgnoreCase("get")) {
                String queryString = "";
                if(savedRequest.getQueryString() != null){
                    queryString = "?" + savedRequest.getQueryString();
                }

                return savedRequest.getRequestUrl() + queryString;
            }
        }

        return "/";
    }

    @RequiresRoles("user")
    @ResponseBody
    @RequestMapping("/error")
    public String error() throws Exception {
        throw new Exception("就是测试错误");
    }
}
