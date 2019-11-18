package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserMapper {
    //返回所有用户信息
    @Select("select* from User")
    List<User> getUserList();
    //按照ID查询用户
    @Select("SELECT * FROM User WHERE ID = #{ID}")
    public User findUserById(int ID);
    //按照名称查询用户
    @Select("select *from user where name=#{name}")
    public User findUserByName(String name);
    //按照名称删除用户
    @Delete("delete from User where ID=#{ID}")
    int DeleteByID(int ID);
    //插入新用户
    @Insert("insert into user(ID,name,password) values(#{ID},#{name},#{password})")
    int InsertUser(int ID, String name,String password);
    //根据ID更新用户信息
    @Update("update user set ID=#{ID},name=#{name},password=#{password} where ID=#{ID}")
    int UpdateUser(int ID,String name,String password);
    //登录验证
    @Select("select count(*) from User where name=#{name} and password=#{password}")
    boolean VerifyUser(int ID,String password);
}
