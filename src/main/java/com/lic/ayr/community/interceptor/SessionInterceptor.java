package com.lic.ayr.community.interceptor;

import com.lic.ayr.community.mapper.UserMapper;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class SessionInterceptor implements HandlerInterceptor {


    @Autowired
    UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @Override//请求处理前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
         * 每一次请求先获取cookie 就是token
         * */
        Cookie[] cookies = request.getCookies();//拿所有cookie 如果第一次没有会报错 就让他直接跳转到index
        if (cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {//遍历cookie
                if (cookie.getName().equals("token")){//看看有没有我们自定义的名为token的cookie
                    String token=cookie.getValue();//有的话获取他的值
                    User user=userMapper.selectToken(token);//给mysql数据库查 查到生成一个对象

                    if (user !=null && user.getId()!=null){//对象不为空的话

                        request.getSession().setAttribute("user",user);//再把它传到页面上
                        Integer count = notificationService.unreadCount(user.getId());
                        request.getSession().setAttribute("count",count);
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
