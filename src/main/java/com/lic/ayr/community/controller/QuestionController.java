package com.lic.ayr.community.controller;

import com.lic.ayr.community.dto.CommentDTO;
import com.lic.ayr.community.dto.QuestionToKenDTO;
import com.lic.ayr.community.service.CommentService;
import com.lic.ayr.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){

        QuestionToKenDTO questionToKenDTO=questionService.getById(id);

        List<CommentDTO> comments=commentService.listByQuestionId(id,1);
        model.addAttribute("question",questionToKenDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
