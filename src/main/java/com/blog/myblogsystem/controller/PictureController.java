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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PictureController {
    @Autowired
    SessionService sessionService;
    @Autowired
    PictureDao pictureDao;
    @RequestMapping(path = "picture/insert",method = RequestMethod.POST)
    Response insertPictures(@RequestBody String body, HttpSession session) throws IOException, ServletException {
        Response response=new Response();
        Picture picture = new Picture();
        picture.setUrl(body.toString());
        String userid=session.getAttribute("id").toString();
        picture.setUserid(Integer.parseInt(userid));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1=new SimpleDateFormat("HH:mm:ss");
        String curTime=df.format(new Date())+" "+df1.format(new Date());
        picture.setTime(curTime);
        pictureDao.insertPicture(picture);
        System.out.println(body);
        return response;
    }
    //获取当前用户的所有图片
    @RequestMapping(path = "picture/get",method = RequestMethod.GET)
    Response getUserPictures(@RequestBody Picture picture,HttpSession session)
    {
        Response response=new Response();
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        String userid=session.getAttribute("id").toString();
        picture.setUserid(Integer.parseInt(userid));
        List<Picture> pictureList=null;
        pictureList=pictureDao.getuserPictures(picture);
        response.setStatus("200");
        response.setError("获取成功");
        response.setResult(pictureList);
        return  response;
    }
    @RequestMapping(path = "picture/delete",method = RequestMethod.DELETE)
    Response deletePicture(@RequestBody Picture picture,HttpSession session)
    {
        Response response=new Response();
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        return response;
    }
}
