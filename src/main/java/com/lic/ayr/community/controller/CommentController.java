package com.lic.ayr.community.controller;

import com.lic.ayr.community.Util.LicReturn;
import com.lic.ayr.community.dto.CommentDTO;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request){
        System.out.println(1);
        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return LicReturn.errof(CustomizeErrorCode.LOGIN_NOT_FOUND);
        }

        commentDTO.setCommentator(user.getId());
        commentService.insert(commentDTO);

        return LicReturn.okof();
    }
}
