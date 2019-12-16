package com.lic.ayr.community.mapper;

import com.lic.ayr.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatarUrl) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatarUrl})")
    void insertMayunUser(User user);

    @Select("SELECT * from user where token = #{token}")//不是类的话 参数要加@Param注解
    User selectToken(@Param("token") String token);

    @Select("SELECT * from user where id = #{creator}")
    User findById(@Param("creator")Integer creator);
}
