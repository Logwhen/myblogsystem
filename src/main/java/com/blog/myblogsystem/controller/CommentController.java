package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.CommentDao;
import com.blog.myblogsystem.entity.Comment;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.service.SessionService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentDao commentDao;
    @Autowired
   SessionService sessionService;
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1=new SimpleDateFormat("HH:mm:ss");
        String curTime=df.format(new Date())+" "+df1.format(new Date());
        comment.setTime(curTime);
        commentDao.insertComment(comment);
        response.setStatus("200");
        response.setError("评论成功");
        return response;
    }
    @RequestMapping(path = "comment/get",method = RequestMethod.GET)
            Response getComments(Comment comment,HttpSession session)
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
