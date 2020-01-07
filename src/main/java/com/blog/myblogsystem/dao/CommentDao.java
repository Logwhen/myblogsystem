package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface CommentDao {
  @Insert("insert into comment(userid,blogid,content,time,replyid) values(#{userid},#{blogid},#{content},#{time},#{replyid})")
  void insertComment(Comment comment);
 @Delete("delete from comment where commentid=#{commentid}")
 void deleteComment(Comment comment);
 @Delete("delete from comment where blogid=#{blogid}")
 void deleteCommentByBlogid(Comment comment);
 @Select("select *from comment where blogid=#{blogid} order by commentid")
  List<Comment> getCommentList(Comment comment);
}
