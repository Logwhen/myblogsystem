package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
public class FriendList {
    int userid;
    int friendid;
}
