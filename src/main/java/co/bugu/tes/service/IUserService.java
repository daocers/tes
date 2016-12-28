package co.bugu.tes.service;

import co.bugu.tes.domain.User;

import java.util.Set;

/**
 * Created by Administrator on 2016/12/28.
 */
public interface IUserService {
    boolean login(User user);

    Set<String> getRoles(Integer id);


    Set<String> getPermissions(Integer id);

    User get(Integer id);

    User findByUsername(String username);
}
