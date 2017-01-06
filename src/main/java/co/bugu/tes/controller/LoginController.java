package co.bugu.tes.controller;

import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
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
    public String login(String username, String password, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            logger.debug("该用户已经登录");
        } else {
            AuthenticationToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            return "redirect:" + getPreUrl(request);
        }
        return "redirect:/";
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
                return savedRequest.getRequestUrl() + "?" + savedRequest.getQueryString() == null ? "" : savedRequest.getQueryString();
            }
        }
        return "/";
    }
}
