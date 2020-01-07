package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.FriendDao;
import com.blog.myblogsystem.entity.FriendList;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.PreparedStatement;

@RestController
public class friendController {
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

}
