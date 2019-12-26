package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Data
public class UserInfo {
    String username;
    int ID;
    int gender;
    String email;
    String phone;
    String introduce;
    String address;
    String background;
    String profilephoto;
}
