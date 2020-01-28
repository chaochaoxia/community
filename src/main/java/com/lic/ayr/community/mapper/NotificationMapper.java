package com.lic.ayr.community.mapper;

import com.lic.ayr.community.model.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier,receiver,type,gmt_create,status,outerId) values (#{notifier},#{receiver},#{type},#{gmt_create},#{status},#{outerId})")
    void insert(Notification notification);

    @Select("select count(1) from notification where notifier = #{userId}")//查跟user动态的总数
    Integer countByUserId(@Param(value = "userId")Integer userId);

    @Select("select count(1) from notification where notifier = #{userId} and status = 0")//查跟user动态的总数
    Integer countByUserIdStatus(@Param(value = "userId")Integer userId);

    @Select("select * from notification where notifier = #{notifier} ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Notification> userlist(@Param(value = "notifier")Integer notifier ,@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select * from notification where id = #{id}")
    Notification selectByKey(Integer id);


    @Update("update notification set status= #{status} where id=#{id}")
    void updataStatus(Notification notification);
}
