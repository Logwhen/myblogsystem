package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Comment {
    int commentid;
    int blogid;
    int userid;
    int replyid;
    String content;
    String time;
    String username;
    String avatar;
}
