package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class Blog {
    int userid;
    int blogid;
    String title;
    String Abstract;
    String content;
    String time;
    int viewtimes;
    int likes;
    int status;//记录当前用户是否给博客点过赞
}
