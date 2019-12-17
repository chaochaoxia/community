package com.lic.ayr.community.controller;


import com.lic.ayr.community.dto.PaginationDTO;
import com.lic.ayr.community.dto.QuestionToKenDTO;

import com.lic.ayr.community.mapper.UserMapper;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String hello(
            Model model,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "5") Integer size){


//        在到首页之前先查询到所有动态和页码数然后发给页面遍历在页面上
        PaginationDTO pagination=questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
