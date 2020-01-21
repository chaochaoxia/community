package com.lic.ayr.community.mapper;

import com.lic.ayr.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface QuesstionMapper {


    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmt_create},#{gmt_modified},#{creator},#{tag})")
     void create(Question question);

    @Select("select * from question ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question")//查动态的总数
    Integer count();

    @Select("select * from question where creator = #{userId} ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Question> userlist(@Param(value = "userId")Integer userId,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")//查跟user动态的总数
    Integer countByUserId(@Param(value = "userId")Integer userid);

    @Select("select * from question where id = #{id}")
    Question getById(@Param(value = "id")Integer id);

    @Update("update question set title =#{title} ,description=#{description},gmt_modified=${gmt_modified},tag=#{tag} where id=#{id}")
    int update(Question question);

    @Update("update question set view_count= view_count + 1 where id=#{id}")
    void updateView(@Param(value = "id")Integer id);

    @Update("update question set comment_count= comment_count + 1 where id=#{id}")
    void updateComment(@Param(value = "id")Integer id);

    @Select("SELECT * FROM question WHERE id !=#{id} and tag regexp #{tag}")
    List<Question> selsectRelated(@Param(value = "id") Integer id,@Param(value = "tag") String tag);

}
