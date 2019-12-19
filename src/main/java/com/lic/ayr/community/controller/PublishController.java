package com.lic.ayr.community.controller;


import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.model.Question;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {



    @Autowired
    QuestionService questionService;

    /**
     * 跳转到发表页面
     * */
    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    /**
     * 跳转到编辑信息
     * */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,Model model){
        Question question = questionService.getquestionById(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());

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
        User user =(User)request.getSession().getAttribute("user");

        if (user==null){//未登录状态 返回错误信息 重新返回发布页面

            model.addAttribute("error","没有登录不能发表动态");
            return "publish";
        }

        question.setCreator(user.getId());
        questionService.create(question);


//        发布成功回到首页
        return "redirect:/";
    }
}
