package com.blog.myblogsystem.controller;

import com.blog.myblogsystem.dao.UserInfoDao;
import com.blog.myblogsystem.entity.Response;
import com.blog.myblogsystem.entity.UserInfo;
import com.blog.myblogsystem.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private SessionService sessionService;
   //编辑个人信息
   @RequestMapping(value="userInfo/modify",method = RequestMethod.POST)
   public Response ModifyPersonalInfo(@RequestBody UserInfo userInfo, HttpSession session) {
       if (sessionService.authority(session).getStatus() != "200") {
           return sessionService.authority(session);
       }
       Response response = new Response();
       String id = session.getAttribute("id").toString();
       userInfo.setID(Integer.parseInt(id));
       try {
           userInfoDao.UpdateUserInfo(userInfo);
           response.setStatus("200");
           response.setError("修改成功");
           return response;
       } catch (Exception e) {
           e.printStackTrace();
           response.setStatus("500");
           response.setError("服务器内部错误");
           return response;
       }
   }
   //前端通过调用此方法获取当前用户个人信息
   @RequestMapping(value="userInfo/get",method = RequestMethod.GET)
   public Response GetUserInfo( HttpSession session)
   {
       if (sessionService.authority(session).getStatus() != "200") {
           return sessionService.authority(session);
       }
       Response response = new Response();
       String id = session.getAttribute("id").toString();
       UserInfo userInfo1=new UserInfo();
       userInfo1.setID(Integer.parseInt(id));
       System.out.println(userInfo1.getID());
       try {
           userInfo1 = userInfoDao.GetUserInfo(userInfo1);
           response.setStatus("200");
           response.setError("返回成功");
           ArrayList<UserInfo> userInfos = new ArrayList<>();
           userInfos.add(userInfo1);
           response.setResult(userInfos);
           return response;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           response.setStatus("500");
           response.setError("系统错误");
           return response;
       }
   }
   @RequestMapping(path = "userInfo/getInfo",method = RequestMethod.POST)
   public Response GetInfoByID(@RequestBody UserInfo userInfo)
   {
       Response response = new Response();
       List<UserInfo> userInfoList=new ArrayList<>();
       userInfoList=userInfoDao.SelectByUserId(userInfo);
       if(userInfoList==null||userInfoList.size()==0)
       {
           response.setStatus("404");
           response.setError("未找到此用户");
           return response;
       }
       else {response.setStatus("200");
       response.setError("查询成功");
       response.setResult(userInfoList);
       return  response;
   }}
}
