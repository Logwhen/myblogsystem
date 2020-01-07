package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.FriendDao;
import com.blog.myblogsystem.dao.UserInfoDao;
import com.blog.myblogsystem.entity.FriendList;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import com.blog.myblogsystem.entity.UserInfo;
import com.blog.myblogsystem.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class friendController {
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    FriendDao friendDao;
    @Autowired
    SessionService sessionService;
    @RequestMapping(path = "friend/subscribe",method = RequestMethod.POST)
    Response Subscribe(@RequestBody FriendList friendList, HttpSession session)
    {
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        Response response=new Response();
        String userid=session.getAttribute("id").toString();
        friendList.setUserid(Integer.parseInt(userid));
        friendDao.subscribe(friendList);
        return response;
    }
    @RequestMapping(path="friend/delete",method = RequestMethod.DELETE)
    Response Delete(@RequestBody FriendList friendList,HttpSession session)
    {
        if (sessionService.authority(session).getStatus()!="200")
        {
            return sessionService.authority(session);
        }
        Response response=new Response();
        String userid=session.getAttribute("id").toString();
        friendList.setUserid(Integer.parseInt(userid));
        friendDao.cancelsubscribe(friendList);
        return response;
    }
    public static  int  getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
    @RequestMapping(path = "friend/recommend",method = RequestMethod.POST)
    Response Recommend(HttpSession session)
    {
        Response response=new Response();
        List<UserInfo> userInfoList=new ArrayList<>();
        userInfoList=friendDao.GetRandomUser();
        response.setResult(userInfoList);
        response.setError("获取成功");
        response.setStatus("200");
       return response;
    }

}
