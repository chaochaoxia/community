package com.lic.ayr.community.controller;

import com.lic.ayr.community.dto.NotificationDTO;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {


    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id")Integer id,HttpServletRequest request){

        User user =(User)request.getSession().getAttribute("user");

        if (user==null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO=notificationService.read(id,user);

        if (notificationDTO.getStatus() == 1){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else {
            return "redirect:/";
        }

    }
}
