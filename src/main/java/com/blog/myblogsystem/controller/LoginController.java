package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//用户登录接口
public class LoginController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public Response Login(@RequestBody User user, HttpSession session)
    {
        Response response=new Response();
        if(userService.Exist(user.getUsername()).getStatus()!="True")
        {
              return userService.Exist(user.getUsername());
        }
        List<User> UserList=userDao.getUser(user);
        if(UserList.size()==0)
        {
            response.setStatus("403");
            response.setError("请输入正确的密码！");
            return response;
        }
        session.setAttribute("id", UserList.get(0).getID());
        response.setStatus("200");
        response.setError(session.getId());
        return response;
    }
}
