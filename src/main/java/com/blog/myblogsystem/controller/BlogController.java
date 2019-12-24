package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.BlogDao;
import com.blog.myblogsystem.entity.Blog;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.service.SessionService;
import com.blog.myblogsystem.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class BlogController {
    @Autowired
    BlogDao blogDao;
    @Autowired
    SessionService sessionService;
    @RequestMapping(path = "blog/write",method = RequestMethod.POST)
    public Response writeBlog(@RequestBody Blog blog, HttpSession session)
    {
        if (sessionService.authority(session).getStatus() != "200") {
            return sessionService.authority(session);
        }
        Response response = new Response();
        String id = session.getAttribute("id").toString();
        blog.setUserid(Integer.parseInt(id));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1=new SimpleDateFormat("HH:mm:ss");
        String curTime=df.format(new Date())+" "+df1.format(new Date());
        System.out.println(curTime);
        blog.setTime(curTime);
        System.out.println(blog.getContent()+blog.getAbstract());
        try {
            blogDao.writeBlog(blog);
        }
        catch (Exception e)
        {

            e.printStackTrace();
            response.setError("服务器内部错误");
            response.setStatus("500");
            return  response;
        }
        response.setError("写入成功");
        response.setStatus("200");
        return response;
    }
    @RequestMapping(path = "blog/search",method = RequestMethod.GET)
    public Response SearchBlog(@Param("searchString")String searchString)
    {
        Response response=new Response();
        List<Blog> blogList=null;
        try{
            blogList=blogDao.searchBlog(searchString);
        }catch (Exception e)
        {
            e.printStackTrace();
            response.setError("服务器内部错误");
            response.setStatus("500");
            return  response;
        }
        if(blogList==null)
        {
            response.setStatus("404");
            response.setError("未找到该博客");
            return  response;
        }
        else
        {
            response.setError("查询成功");
            response.setStatus("200");
            response.setResult(blogList);
            return response;
        }
    }
}
