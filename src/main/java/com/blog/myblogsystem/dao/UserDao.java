package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface UserDao {
    //返回所有用户信息
    List<User> getUsers(User user);
    void DeleteUser(User user);
    void UpdateUser(User user);
    void insertUser(User user);
    List<User> getUser(User user);

    @Select("select from user where ID=#{ID}")
    List<User> getUsername(User user);
}
