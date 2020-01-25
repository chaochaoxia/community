package com.lic.ayr.community.service;


import com.lic.ayr.community.mapper.UserMapper;

import com.lic.ayr.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user){
        User dbuser = userMapper.seleAccount_id(user.getAccount_id()); //先去数据库查找有没有相同id的人
        if (dbuser==null){//添加

            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insertMayunUser(user);

        }else {//更新
            dbuser.setGmt_modified(System.currentTimeMillis());
            dbuser.setToken(user.getToken());
            dbuser.setName(user.getName());
            dbuser.setAvatar_url(user.getAvatar_url());
            userMapper.updateUser(dbuser);
        }

    }
}
