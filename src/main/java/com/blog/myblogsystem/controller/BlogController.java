package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.BlogDao;
import com.blog.myblogsystem.dao.CommentDao;
import com.blog.myblogsystem.dao.FavouratesDao;
import com.blog.myblogsystem.dao.UserInfoDao;
import com.blog.myblogsystem.entity.*;
import com.blog.myblogsystem.service.SessionService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class BlogController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    CommentDao commentDao;
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
    @RequestMapping(path = "blog/search",method = RequestMethod.POST)
    public Response SearchBlog(@RequestBody Blog blog)
    {
        Response response=new Response();
        List<Blog> blogList=null;
        try{
            blogList=blogDao.searchBlog(blog.getTitle());
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
            for(int i=0;i<blogList.size();i++)
            {
                Blog blog1=new Blog();
                blog1.setUserid(blogList.get(i).getUserid());
                blog1.setBlogid(blogList.get(i).getBlogid());
                if(blogDao.CheckLikes(blog1)!=null&&blogDao.CheckLikes(blog1).size()!=0)
                {
                    blogList.get(i).setStatus(1);
                }
                else  blogList.get(i).setStatus(0);
            }
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
            Comment comment=new Comment();
            comment.setBlogid(blog.getBlogid());
            commentDao.deleteComment(comment);
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
    @RequestMapping(path = "blog/view",method = RequestMethod.POST)
    public Response viewBlogs(@RequestBody Blog blog)
    {
        Response response=new Response();
        List<Blog> blogList=null;
        try{
            blogList=blogDao.getUserBlogs(blog);
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
    @RequestMapping(path = "blog/getById",method = RequestMethod.POST)
    public Response getBlogByID(@RequestBody Blog blog)
    {
        Response response=new Response();
        List<Blog> blogList=null;
        try{
            blogList=blogDao.getBlog(blog.getBlogid());
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
    @RequestMapping(path = "blog/share",method = RequestMethod.GET)
    public Response getAllBlogs()
    {
        Response response=new Response();
        List<Blog> blogList=blogDao.getAllBlogs();
        List<PersonalPost> personalPosts=new ArrayList<>();

        for(int i=0;i<blogList.size();i++)
        {

            Blog blog1=new Blog();
            blog1.setUserid(blogList.get(i).getUserid());
            blog1.setBlogid(blogList.get(i).getBlogid());
            if(blogDao.CheckLikes(blog1)!=null&&blogDao.CheckLikes(blog1).size()!=0)
            {
                    blogList.get(i).setStatus(1);
            }
            else  blogList.get(i).setStatus(0);
            int id=blogList.get(i).getUserid();
            UserInfo userInfo=new UserInfo();
            userInfo.setID(id);
            userInfo=userInfoDao.GetUserInfo(userInfo);
            PersonalPost personalPost=new PersonalPost();
            personalPost.setBlog(blogList.get(i));
            personalPost.setProfilephoto(userInfo.getProfilephoto());
            personalPost.setUsername(userInfo.getUsername());
            personalPosts.add(personalPost);
        }
        response.setStatus("200");
        response.setStatus("访问成功");
        response.setResult(personalPosts);
        return response;
    }
    @RequestMapping(path = "blog/viewtimes",method = RequestMethod.POST)
    String viewTimes(@RequestBody Blog blog)
    {
        blog.setViewtimes(blog.getViewtimes()+1);
        blogDao.updateBlog(blog);
        return String.valueOf(blog.getViewtimes());
    }
    @RequestMapping(path = "blog/likes",method = RequestMethod.POST)
    Response Likes(@RequestBody Blog blog,HttpSession session )
    {
        if (sessionService.authority(session).getStatus() != "200") {
            return sessionService.authority(session);
        }
        Response response = new Response();
        String id = session.getAttribute("id").toString();
        blog.setUserid(Integer.parseInt(id));
        blog.setStatus(1);
        blog.setLikes(blogDao.getBlog(blog.getBlogid()).get(0).getLikes()+1);
        blogDao.updateBlog(blog);
        blogDao.addLikes(blog);
        response.setStatus("200");
        response.setError("点赞成功");
        return  response;
    }
    @RequestMapping(path = "blog/cancelLikes",method = RequestMethod.POST)
    Response CancelLikes(@RequestBody Blog blog,HttpSession session)
    {
        if (sessionService.authority(session).getStatus() != "200") {
            return sessionService.authority(session);
        }
        Response response = new Response();
        String id = session.getAttribute("id").toString();
        blog.setUserid(Integer.parseInt(id));
        blog.setStatus(0);
        blog.setLikes(blogDao.getBlog(blog.getBlogid()).get(0).getLikes()-1);
        blogDao.updateBlog(blog);
        blogDao.cancelLikes(blog);
        response.setStatus("200");
        response.setError("点赞成功");
        return  response;
    }
    @RequestMapping(path = "blog/getviewtimes",method = RequestMethod.POST)
    String getViewTimes(@RequestBody Blog blog)
    {
        return String.valueOf(blog.getViewtimes());
    }
}
