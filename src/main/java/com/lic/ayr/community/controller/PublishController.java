package com.lic.ayr.community.controller;


import com.lic.ayr.community.mapper.MayunUserMapper;
import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.model.Question;
import com.lic.ayr.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuesstionMapper quesstionMapper;

    @Autowired
    MayunUserMapper mayunUserMapper;
    
    /**
     * 跳转到发表页面
     * */
    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }


    /**
     * 发布功能
     * */
    @PostMapping("/publish")
    public String dopublish(
            Question question,
            HttpServletRequest request,
            Model model) {
        //        为了发生错误回显页面的数据不会丢失
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());

        if (question.getTitle() ==null || question.getTitle()==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (question.getDescription() ==null || question.getDescription()==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (question.getTag() ==null || question.getTag()==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user=null;//初始化一个uesr对象为空

            Cookie[] cookies = request.getCookies();
            if (cookies !=null && cookies.length!=0){
                for (Cookie cookie : cookies) {//遍历cookie
                    if (cookie.getName().equals("token")){//看看有没有我们自定义的名为token的cookie
                        String token=cookie.getValue();//有的话获取他的值
                        user=mayunUserMapper.selectToken(token);//给h2数据库查 查到生成一个对象
                        if (user !=null && user.getId()!=null){//对象不为空的话
                            request.getSession().setAttribute("user",user);//再把它传到页面上
                        }
                        break;
                    }
                }
            }
        if (user==null){//未登录状态 返回错误信息 重新返回发布页面

            model.addAttribute("error","没有登录不能发表动态");
            return "publish";
        }
//        Question question = new Question();//new 一个Question 对象
//        //把页面的参数都传进来赋值到对象中 然后保存到数据库
//        question.setTitle(title);
//        question.setDescription(description);
//        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());

        quesstionMapper.create(question);

//        发布成功回到首页
        return "redirect:/";
    }
}
