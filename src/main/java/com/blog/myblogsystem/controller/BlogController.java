package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.BlogDao;
import com.blog.myblogsystem.entity.Blog;
import com.blog.myblogsystem.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
    @Autowired
    BlogDao blogDao;
    @RequestMapping(path = "",method = RequestMethod.POST)
    public Response writeBlog(Blog blog)
    {
        Response response=new Response();


        return response;
    }
}
