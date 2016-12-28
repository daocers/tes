package co.bugu.tes.service.impl;

import co.bugu.tes.domain.User;
import co.bugu.tes.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Administrator on 2016/12/28.
 */
@Service
public class UserServiceImpl implements IUserService {
    public boolean login(User user) {
        return true;
    }

    public Set<String> getRoles(Integer id) {
        return null;
    }

    public Set<String> getPermissions(Integer id) {
        return null;
    }

    public User get(Integer id) {
        return null;
    }

    public User findByUsername(String username) {
        return null;
    }
}