package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.Picture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureDao {
    void insertPicture(Picture picture);
    void insertPictures(List<Picture> pictures);
    @Delete("delete from pictures where pictureid=#{pictureid}")
    void deletPicture(Picture picture);
    //获取指定album下的所有Pictures
    List<Picture> getAlbum(Picture picture);
    //获取指定userid的所有pictures
    List<Picture> getuserPictures(Picture picture);
}
