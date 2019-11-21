package com.blog.myblogsystem.service;

import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
@Service
public class SessionService {
    public Response authority(HttpSession session){
        User user =new User();
        Response response =new Response();
        if(session.getAttribute("id")==null){
            response.setStatus("401");
            response.setError("请先登录");
            return response;
        }
        user.setID(Integer.parseInt(session.getAttribute("id").toString()));
        System.out.println(user.getID());
        response.setStatus("200");
        return response;
    }
}
