package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

@Data
@Setter
@Getter
public class PersonalPost {

  Blog blog;
  String username;
  String profilephoto;
}
