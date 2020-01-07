package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.Blog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface BlogDao {
    void writeBlog(Blog blog);
    void deleteBlog(Blog blog);
    @Update("update blog set likes=#{likes},status=#{status} where blogid=#{blogid}")
    void updateBlog(Blog blog);
    List<Blog> getBlog(int blogid);
    //获取当前用户所有博客接口
    List<Blog>getUserAllBlogs(Blog blog);
    //查询博客接口
    List<Blog>searchBlog(String searchString);
    //查看他人所有博客
    List<Blog>viewotherBlogs(int userid);
    @Select("select * from blog where blogid=#{blogid} order by viewtimes")
    List<Blog>getBlogOrderbyViewTimes(Blog blog);
    @Insert("insert into Likes(userid,blogid) values(#{userid},#{blogid})")
    void addLikes(Blog blog);
    @Select("select * from Likes where userid=#{userid} and blogid=#{blogid}")
    List<Blog> CheckLikes(Blog blog);
    @Delete("delete from likes where userid=#{userid} and blogid=#{blogid}")
    void cancelLikes(Blog blog);
    //获取指定id对应的博客
    @Select("select *from blog where userid=#{userid}")
    List<Blog> getUserBlogs(Blog blog);
}
