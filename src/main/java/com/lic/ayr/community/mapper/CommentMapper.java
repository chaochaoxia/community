package com.lic.ayr.community.mapper;


import com.lic.ayr.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {


    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,content) values (#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{content})")
     void insert(Comment comment);

    @Select("select * from comment where parent_id = #{parent_id}")
    Comment selectById(@Param(value = "parent_id") Integer parent_id);

    @Select("select * from comment where parent_id = #{parent_id} order by gmt_create desc")
    List<Comment> selectByIds(@Param(value = "parent_id") Integer parent_id);

    @Update("update comment set comment_count= comment_count + 1 where id=#{id}")
    void updateCount(@Param(value = "id") Integer id);
}
