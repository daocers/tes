package co.bugu.tes.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2017/1/7.
 */
public class BuguExceptionHandler implements HandlerExceptionResolver {
    private static Logger logger = LoggerFactory.getLogger(BuguExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception e) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("ex", e);

        if (e instanceof UnauthorizedException) {
            logger.error("授权失败", e);
            return new ModelAndView("unauthorized", model);

        } else if (e instanceof AccountNotFoundException) {
            logger.error("尝试登陆，用户不存在：", e);
            return new ModelAndView("unauthorized", model);
        } else {
            logger.error("捕获到异常：", e);
            return new ModelAndView("500", model);
        }

    }
}
