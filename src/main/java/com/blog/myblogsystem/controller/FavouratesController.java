package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.BlogDao;
import com.blog.myblogsystem.dao.FavouratesDao;
import com.blog.myblogsystem.entity.Blog;
import com.blog.myblogsystem.entity.Favourates;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPath;
import java.util.List;

@RestController
public class FavouratesController {
    //添加到收藏夹
    @Autowired
    SessionService sessionService;
    @Autowired
    FavouratesDao favouratesDao;
    @Autowired
    BlogDao blogDao;
    @RequestMapping(path = "favourates/insert",method = RequestMethod.POST)
    Response addFavourates(@RequestBody Favourates favourates, HttpSession session)
    {
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        Response response=new Response();
        String userid=session.getAttribute("id").toString();
        favourates.setUserid(userid);
        favouratesDao.addfavourates(favourates);
        response.setError("收藏成功");
        response.setStatus("200");
        return response;
    }
    @RequestMapping(path = "favourates/delete",method = RequestMethod.DELETE)
    Response deleteFavourates(Favourates favourates,HttpSession session)
    {
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        Response response=new Response();
        String userid=session.getAttribute("id").toString();
        favourates.setUserid(userid);
        favouratesDao.deleteFavourates(favourates);
        response.setStatus("200");
        response.setError("移除成功");
        return  response;
    }
    //获取当前用户的收藏夹
    @RequestMapping(path = "favourates/get",method = RequestMethod.GET)
    Response getFavourates(HttpSession session)
    {
        Favourates favourates = new Favourates();
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        Response response=new Response();
        String userid=session.getAttribute("id").toString();
        favourates.setUserid(userid);
        List<Favourates>favouratesList=favouratesDao.getFavouratesList(favourates);
        List<Blog> blogList=null;
        for(int i=0;i<favouratesList.size();i++) {
            Blog blog=new Blog();
            blog.setBlogid(Integer.parseInt(favouratesList.get(i).getBlogid()));
            blogList.add(blogDao.getBlogByBlogid(blog));
        }
        response.setStatus("200");
        response.setError("获取成功");
        response.setResult(favouratesList);
        return response;
    }

}
