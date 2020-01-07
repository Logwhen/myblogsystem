package com.blog.myblogsystem.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.annotations.Select;

@Data
@Getter
@Setter
@ToString
public class passwordCheck {
    String oldPassword;
    String newPassword;
}
