package com.blog.myblogsystem.entity;

import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Response<T> {
public String status;
public String error;
List<T> result;
}
