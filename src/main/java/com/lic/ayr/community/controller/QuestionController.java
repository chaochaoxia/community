package com.lic.ayr.community.controller;

import com.lic.ayr.community.dto.QuestionToKenDTO;
import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model){

        QuestionToKenDTO questionToKenDTO=questionService.getById(id);
        //累加阅读数
//        questionService.incView(id);
        model.addAttribute("question",questionToKenDTO);
        return "question";
    }
}
