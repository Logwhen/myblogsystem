package com.blog.myblogsystem.dao;

import com.blog.myblogsystem.entity.Favourates;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface FavouratesDao {
    @Insert("insert into favourates(userid,blogid) values(#{userid},#{blogid})")
    void addfavourates(Favourates favourates);
    @Delete("delete from favourates where userid=#{userid} and blogid=#{blogid}")
    void deleteFavourates(Favourates favourates);
    @Delete("delete * from favourates where blogid=#{blogid}")
    void deleteFromFavourates(Favourates favourates);
    @Select("select * from favourates where userid=#{userid}")
    List<Favourates> getFavouratesList(Favourates favourates);
}
