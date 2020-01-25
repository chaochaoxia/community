package com.lic.ayr.community.mapper;

import com.lic.ayr.community.model.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{account_id},#{token},#{gmt_create},#{gmt_modified},#{avatar_url})")
    void insertMayunUser(User user);

    @Select("SELECT * from user where account_id = #{account_id}")
    User seleAccount_id(@Param("account_id")String account_id);

    @Select("SELECT * from user where token = #{token}")//不是类的话 参数要加@Param注解
    User selectToken(@Param("token") String token);

    @Select("SELECT * from user where id = #{creator}")
    User findById(@Param("creator")Integer creator);

    @Update("UPDATE user SET name=#{name},token =#{token},gmt_modified=#{gmt_modified},avatar_url=#{avatar_url} where id=#{id}")
    void updateUser(User dbuser);
}
