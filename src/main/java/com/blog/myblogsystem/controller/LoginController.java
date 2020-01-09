package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.entity.passwordCheck;
import com.blog.myblogsystem.service.UserService;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//用户登录接口
public class LoginController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Response Logout(@RequestBody User user, HttpSession session) {
        Response response = new Response();
        session.removeAttribute("id");
        response.setStatus("200");
        response.setError(session.getId());
        return response;
    }

    @RequestMapping(value = "password/modify", method = RequestMethod.POST)
    public Response Modify(@RequestBody passwordCheck passwordCheck, HttpSession session)
    {
        Response response=new Response();
        User user=new User();
        user.setID(Integer.parseInt(session.getAttribute("id").toString()));
        user.setPassword(passwordCheck.getOldPassword());
        System.out.println(user.getPassword());
        List<User> UserList=userDao.passwordCheck(user);
        if(UserList.size()==0)
        {
            response.setStatus("403");
            response.setError("请输入正确的密码！");
            return response;
        }
        else
            user.setPassword(passwordCheck.getNewPassword());
          userDao.UpdateUser(user);
          response.setStatus("200");
          response.setError("修改成功");
          return  response;
    }


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
