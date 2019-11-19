package com.blog.myblogsystem.controller;

import com.alibaba.fastjson.JSON;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.service.UserService;
import com.blog.myblogsystem.service.userserviceImpl.UserServiceImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController
{
@Autowired
    UserServiceImpl userService;
@GetMapping("/allusers")
    public List<User> getAllUsers()
    {
       List<User> users=userService.getAllUsers();
       return users;
    }
    @RequestMapping ("/login")
    public String Login(HttpServletRequest req, HttpSession session) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        String account=req.getParameter("account");
        String password=req.getParameter("password");
        boolean response=userService.VerifyUser(account,password);
        if(response)
        {
             jsonObject.put("code",1);
             jsonObject.put("msg","登录成功！");
             session.setAttribute("account",account);
             return jsonObject.toString();
        }
        else{
            jsonObject.put("code",0);
            jsonObject.put("msg","用户名或密码错误");
            return jsonObject.toString();
        }
    }
}
