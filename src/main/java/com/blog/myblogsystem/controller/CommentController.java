package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.CommentDao;
import com.blog.myblogsystem.dao.UserDao;
import com.blog.myblogsystem.dao.UserInfoDao;
import com.blog.myblogsystem.entity.Comment;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.entity.UserInfo;
import com.blog.myblogsystem.service.SessionService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentDao commentDao;
    @Autowired
    UserDao userDao;
    @Autowired
   SessionService sessionService;
    @Autowired
    UserInfoDao userInfoDao;
    @RequestMapping(value = "comment/add",method = RequestMethod.POST)
    Response addComment(@RequestBody Comment comment, HttpSession session)
    {
        Response response=new Response();
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        String userid=session.getAttribute("id").toString();
        comment.setUserid(Integer.parseInt(userid));
        User user=new User();
        user.setID(Integer.parseInt(userid));
        user=userDao.getUser(user).get(0);
        comment.setUsername(user.getUsername());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1=new SimpleDateFormat("HH:mm:ss");
        String curTime=df.format(new Date())+" "+df1.format(new Date());
        comment.setTime(curTime);
        UserInfo userInfo=new UserInfo();
        userInfo.setID(Integer.parseInt(userid));
        comment.setAvatar(userInfo.getProfilephoto());
        commentDao.insertComment(comment);
        response.setStatus("200");
        response.setError("评论成功");
        return response;
    }
    @RequestMapping(path="comment/delete",method = RequestMethod.DELETE)
    Response deleteComment(@RequestBody Comment comment,HttpSession session)
    {
        Response response=new Response();
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        commentDao.deleteComment(comment);
        response.setError("删除成功");
        response.setStatus("200");
        return  response;
    }
    @RequestMapping(path = "comment/get",method = RequestMethod.POST)
    Response getComments(@RequestBody Comment comment, HttpSession session)
    {

        Response response=new Response();
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        List<Comment>CommentList=null;
        CommentList=commentDao.getCommentList(comment);
        response.setError("200");
        response.setStatus("获取成功");
        response.setResult(CommentList);
        return response;
    }
}
