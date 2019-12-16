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
    String abstrac;
    String content;
    String time;
}
