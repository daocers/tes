package co.bugu.tes.shiro;

import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 * Created by Administrator on 2016/12/28.
 */
public class JdbcRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(JdbcRealm.class);

    @Autowired
    IUserService<User> userService;

    /**
     * 为当前登录成功的用户授权和角色，
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer id = (Integer) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(userService.getRoles(id));
//        authorizationInfo.setStringPermissions(userService.getPermissions(id));
        return authorizationInfo;
    }

    /**
     * 验证当前用户，获取认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        char[] password = token.getPassword();
        logger.debug("登录，当前用户：{}", username);

        if(StringUtils.isEmpty(username)){
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for(char c : password){
            buffer.append(c);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(buffer.toString());
        List<User> userList = userService.findByObject(user);
        if(userList == null || userList.size() == 0){
            return null;
        }
        user = userList.get(0);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        return info;
    }
}
