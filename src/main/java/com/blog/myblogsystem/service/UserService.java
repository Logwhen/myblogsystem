package com.blog.myblogsystem.service;

import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public Response Exist(String username){
        Response response =new Response();
        User user = new User();
        user.setUsername(username);
        if(userDao.getUser(user).size()==0){
            response.setStatus("false");
            response.setError("请输入正确的用户名！");
            return response;
        }
        response.setStatus("True");
        return response;
    }
}
