package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class Picture {
    int pictureid;
    int userid;
    String time;
    String url;
    String albumname;
}
