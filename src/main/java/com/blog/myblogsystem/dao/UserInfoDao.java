package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserInfoDao {
   void UpdateUserInfo(UserInfo userInfo);
   UserInfo GetUserInfo(UserInfo userInfo);
   void InsertUserInfo(UserInfo userInfo);
   void DeleteUserInfo(UserInfo userInfo);
   @Select("Select * from UserInformation where ID=#{ID}")
   List<UserInfo> SelectByUserId(UserInfo userInfo);
   @Select("Select * from UserInformation where username like #{username}")
   List<UserInfo> FindUser(UserInfo userInfo);
}
