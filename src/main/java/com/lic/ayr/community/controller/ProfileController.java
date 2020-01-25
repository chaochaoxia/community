package com.lic.ayr.community.controller;

import com.lic.ayr.community.dto.PaginationDTO;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
import com.lic.ayr.community.mapper.UserMapper;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.NotificationService;
import com.lic.ayr.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;


    @GetMapping("profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size){
        User user =(User)request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }

        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的动态");
            PaginationDTO pagination = questionService.userlist(user.getId(), page, size);
            model.addAttribute("pagination",pagination);

        }else if ("replies".equals(action)){
            Integer unreadCount=0;
            PaginationDTO pagination = notificationService.userlist(user.getId(), page, size);
            try {
                unreadCount=notificationService.unreadCount(user.getId());
            }catch (Exception e){
                throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
            }

            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("unreadCount",unreadCount);
            model.addAttribute("pagination",pagination);
        }


        return "profile";
    }
}
