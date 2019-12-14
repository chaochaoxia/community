package com.lic.ayr.community.controller;


import com.lic.ayr.community.mapper.MayunUserMapper;
import com.lic.ayr.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    MayunUserMapper mayunUserMapper;


    @GetMapping("/")
    public String hello(HttpServletRequest request){
        /*
        * 在进入首页前就先获取cookie 就是token
        * */
        Cookie[] cookies = request.getCookies();//拿cookie
        for (Cookie cookie : cookies) {//遍历cookie
            if (cookie.getName().equals("token")){//看看有没有我们自定义的名为token的cookie
                String token=cookie.getValue();//有的话获取他的值
                User user=mayunUserMapper.selectToken(token);//给h2数据库查 查到生成一个对象
                if (user !=null){//对象不为空的话
                    request.getSession().setAttribute("user",user);//再把它传到页面上
                }
                break;
            }
        }

        return "index";
    }
}
