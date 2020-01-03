package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface BlogDao {
    void writeBlog(Blog blog);
    void deleteBlog(Blog blog);
    void updateBlog(Blog blog);
    //获取当前用户所有博客接口
    List<Blog>getUserAllBlogs(Blog blog);
    //查询博客接口
    List<Blog>searchBlog(String searchString);
    //查看他人所有博客
    List<Blog>viewotherBlogs(int userid);

}
