package com.lic.ayr.community.controller;


import com.lic.ayr.community.mapper.MayunUserMapper;
import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.model.Question;
import com.lic.ayr.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    MayunUserMapper mayunUserMapper;

    @Autowired
    QuesstionMapper quesstionMapper;
    @GetMapping("/")
    public String hello(HttpServletRequest request){
        /*
        * 在进入首页前就先获取cookie 就是token
        * */
        Cookie[] cookies = request.getCookies();//拿cookie 如果第一次没有会报错 就让他直接跳转到index
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {//遍历cookie
                if (cookie.getName().equals("token")){//看看有没有我们自定义的名为token的cookie
                    String token=cookie.getValue();//有的话获取他的值
                    User user=mayunUserMapper.selectToken(token);//给h2数据库查 查到生成一个对象

                    if (user !=null && user.getId()!=null){//对象不为空的话
                        request.getSession().setAttribute("user",user);//再把它传到页面上
                    }
                    break;
                }
            }
        }

//        在到首页之前先查询到所有动态然后发给页面遍历在页面上
        List<Question> questionsList=quesstionMapper.list();
        return "index";
    }
}
