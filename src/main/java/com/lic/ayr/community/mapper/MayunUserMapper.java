package com.lic.ayr.community.mapper;

import com.lic.ayr.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MayunUserMapper {

    @Insert("insert into user (name,account_id,token,gmt_Create,gmt_Modified) values (#{name},#{account_id},#{token},#{gmt_Create},#{gmt_Modified})")
    void insertMayunUser(User user);

}
