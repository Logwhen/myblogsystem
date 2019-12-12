package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.dao.UserInfoDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.entity.UserInfo;
import com.blog.myblogsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SignUpController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoDao userInfoDao;
    @RequestMapping(value = "/SignUp",method = RequestMethod.POST)
    public Response SignUp(@RequestBody User user)
    {
        Response response=new Response();
        if(userService.Exist(user.getUsername()).getStatus()=="True")
        {
           response.setStatus("403");
           response.setError("此用户名已注册，请重新输入");
           return response;
        }
        try {
            userDao.insertUser(user);
            UserInfo userInfo=new UserInfo();
            userInfo.setID(user.getID());
            userInfo.setUsername(user.getUsername());
            userInfoDao.InsertUserInfo(userInfo);

        } catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus("500");
            response.setError("信息格式错误，请检查信息格式！");
            return response;
        }
        response.setStatus("200");
        response.setError("注册成功");
        return response;
    }
}
