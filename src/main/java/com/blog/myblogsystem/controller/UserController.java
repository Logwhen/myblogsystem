package com.blog.myblogsystem.controller;
import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.service.SessionService;
import com.blog.myblogsystem.service.UserService;
import org.apache.catalina.Session;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController

public class UserController
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    //修改密码
    @RequestMapping("user/ModifyPassword")
    public Response ModifyPassword(@RequestBody User user,HttpSession session)
    {
       if (sessionService.authority(session).getStatus()!="200")
       {
           return sessionService.authority(session);
       }
        Response response=new Response();
       String id=session.getAttribute("id").toString();
        System.out.println(session.getAttribute("id"));
        user.setID(Integer.parseInt(id));
        System.out.println(user.getID());
        try{
            userDao.UpdateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            response.setError("500");
            response.setStatus("输入格式错误,清重新输入。");
            return response;
        }
        response.setStatus("200");
        response.setError("修改成功");
        return response;
    }


}
