package com.lic.ayr.community.controller;

import com.lic.ayr.community.Util.LicReturn;
import com.lic.ayr.community.dto.CommentCreateDTO;
import com.lic.ayr.community.dto.CommentDTO;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");

        if (user==null){
            return LicReturn.errof(CustomizeErrorCode.LOGIN_NOT_FOUND);
        }
        if (commentCreateDTO==null || commentCreateDTO.getContent()==null||commentCreateDTO.getContent()=="") {
            return LicReturn.errof(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        commentCreateDTO.setCommentator(user.getId());
        commentService.insert(commentCreateDTO);

        return LicReturn.okof();
    }

    @ResponseBody
    @GetMapping("/comment/{id}")
    public LicReturn comments(@PathVariable(name = "id")Integer id){
        List<CommentDTO> commentDTOS = commentService.listByQuestionId2(id);
        return LicReturn.okof(commentDTOS);
    }
}
