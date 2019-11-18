package com.blog.myblogsystem.service;

import com.blog.myblogsystem.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    //获取全部用户信息的接口
   List<User> getAllUsers();
   //验证用户登录接口
   boolean VerifyUser(int ID,String password);
}
