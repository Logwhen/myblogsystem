package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.BlogDao;
import com.blog.myblogsystem.dao.FavouratesDao;
import com.blog.myblogsystem.entity.Blog;
import com.blog.myblogsystem.entity.Favourates;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.service.SessionService;
import com.blog.myblogsystem.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class BlogController {
    @Autowired
    FavouratesDao favouratesDao;
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
    //展示当前用户所有blog
    @RequestMapping(path = "blog/getcur",method = RequestMethod.GET)
    public Response CurBlog(HttpSession session)
    {
        if (sessionService.authority(session).getStatus() != "200") {
            return sessionService.authority(session);
        }
        List<Blog> blogList;
        Blog blog=new Blog();
        Response response = new Response();
        String id = session.getAttribute("id").toString();
        blog.setUserid(Integer.parseInt(id));
        try{
            blogList=blogDao.getUserAllBlogs(blog);
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
    @RequestMapping(path = "blog/delete",method = RequestMethod.DELETE)
    public Response deleteBlog(@RequestBody Blog blog,HttpSession session)
    {
        if (sessionService.authority(session).getStatus() != "200") {
            return sessionService.authority(session);
        }


        System.out.println(blog.getBlogid());
        Response response = new Response();
        try{
            Favourates favourates=new Favourates();
            favourates.setBlogid(String.valueOf(blog.getBlogid()));
            favouratesDao.deleteFromFavourates(favourates);
            blogDao.deleteBlog(blog);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setError("服务器内部错误");
            response.setStatus("500");
            return  response;
        }
            response.setError("删除成功");
            response.setStatus("200");
            return response;

    }
    @RequestMapping(path = "blog/view",method = RequestMethod.GET)
    public Response viewBlogs(@RequestParam("userid") String userid)
    {
        Response response=new Response();
        List<Blog> blogList=null;
        try{
            blogList=blogDao.viewotherBlogs(Integer.parseInt(userid));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus("500");
            response.setError("服务器内部错误");
            return response;
        }
        response.setStatus("200");
        response.setStatus("访问成功");
        response.setResult(blogList);
        return response;
    }
    @RequestMapping(path = "blog/viewtimes",method = RequestMethod.POST)
    String viewTimes(@RequestBody Blog blog)
    {
        blog.setViewtimes(blog.getViewtimes()+1);
        return String.valueOf(blog.getViewtimes());
    }
    @RequestMapping(path = "blog/getviewtimes",method = RequestMethod.POST)
    String getViewTimes(@RequestBody Blog blog)
    {
        return String.valueOf(blog.getViewtimes());
    }
}
