package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.PictureDao;
import com.blog.myblogsystem.entity.Picture;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.service.SessionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class PictureController {
    @Autowired
    SessionService sessionService;
    @Autowired
    PictureDao pictureDao;
    @RequestMapping(path = "picture/insert",method = RequestMethod.POST)
    Response insertPictures(@RequestBody String body, HttpSession session) throws IOException, ServletException {
        Response response=new Response();
        System.out.println(body);
        return response;
    }
}
