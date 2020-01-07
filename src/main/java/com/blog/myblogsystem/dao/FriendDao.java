package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.FriendList;
import com.blog.myblogsystem.entity.UserInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface FriendDao {
    @Select("select * from UserInformation order by ran() limit 5")
    List<UserInfo> GetRandomUser();
    @Insert("insert into friendlist values(#{userid},#{friendid})")
    void subscribe(FriendList friendList);
    @Delete("delete from friendlist where friendid=#{friendid}")
    void cancelsubscribe(FriendList friendList);
    @Select("select * from friendlist where userid=#{friendid} ")
    List<FriendList> getSubscribeList(int userid);
}
