package co.bugu.tes.service.impl;


import co.bugu.framework.service.impl.BaseServiceImpl;
import co.bugu.tes.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl<User> extends BaseServiceImpl<User> implements IUserService<User>  {
}
