package co.bugu.tes.shiro;

import co.bugu.tes.domain.Authority;
import co.bugu.tes.domain.Role;
import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * Created by Administrator on 2016/12/28.
 */
public class JdbcRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(JdbcRealm.class);

    @Autowired
    IUserService userService;

    /**
     * 权限认证 获取登录用户的权限
     * 为当前登录成功的用户授权和角色，
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Integer id = (Integer) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = userService.findById(id);
        Set<String> roles = new HashSet<>();
        if(user.getRoleList().size() > 0){
            for(Role role: user.getRoleList()){
                roles.add(role.getCode());
            }
        }


        authorizationInfo.setRoles(roles);
        Set<String> authorities = new HashSet<>();
        if(user.getAuthorityList().size() > 0){
            for(Authority authority: user.getAuthorityList()){
                authorities.add(authority.getUrl());
            }
        }
        authorizationInfo.setStringPermissions(authorities);
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
        if(userList.size() > 1){
            try {
                throw new Exception("用户异常");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("异常：查找到两个以上用户。");
            }
        }
        user = userList.get(0);
        AuthenticationInfo info = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), getName());
        return info;
    }
}
