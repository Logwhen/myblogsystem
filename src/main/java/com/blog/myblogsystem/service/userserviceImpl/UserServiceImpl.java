package com.blog.myblogsystem.service.userserviceImpl;

import com.blog.myblogsystem.dao.UserMapper;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
@Autowired
    UserMapper userMapper;
    @Override
    public List<User> getAllUsers() {
        List<User> Users;
        Users=userMapper.getUserList();
        return Users;
    }
    @Override
    public boolean VerifyUser(int ID, String password) {
        boolean VerifyStatus=userMapper.VerifyUser(ID,password);
        return VerifyStatus;
    }
}
