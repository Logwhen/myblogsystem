package com.blog.myblogsystem.controller;
import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.dao.UserInfoDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.entity.UserInfo;
import com.blog.myblogsystem.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserInfoDao userInfoDao;
   //按照ID或者用户名模糊查找用户
    @RequestMapping(value="user/findUser",method = RequestMethod.POST)
    public Response FindUser(@RequestBody User user,HttpSession session)
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
            List<User> userList;
            userList=userDao.getUsers(user);
            if(userList.size()==0)
            {
                response.setStatus("404");
                response.setError("未找到此用户");
                return response;
            }
        else
            {
                response.setStatus("200");
                response.setError("查询成功！");
                response.setResult(userList);
                return  response;
             }
        }catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus("500");
            response.setError("服务器内部错误");
            return response;
        }
    }
    //修改密码
    @RequestMapping(value = "user/ModifyPassword",method =RequestMethod.PUT)
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
//    @RequestMapping(value = "/modify-personal-info",method = RequestMethod.PUT)
//    public Response ModifyPersonalInfo(@RequestMapping)
}
