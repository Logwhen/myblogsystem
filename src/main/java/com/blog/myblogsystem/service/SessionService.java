package com.blog.myblogsystem.service;

import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.User;

import javax.servlet.http.HttpSession;

public class SessionService {
    public Response authority(HttpSession session){
        User user =new User();
        Response response =new Response();
        if(session.getAttribute("id")==null){
            response.setStatus("401");
            response.setError("请先登录");
            return response;
        }
        response.setStatus("200");
        return response;
    }
}
