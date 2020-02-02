package com.lic.ayr.community.controller;

import com.lic.ayr.community.dto.PaginationDTO;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
import com.lic.ayr.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

        @Autowired
        QuestionService questionService;


        @GetMapping("/searchs")
        public String search(
                Model model,
                @RequestParam(value = "page",defaultValue = "1") Integer page,
                @RequestParam(value = "size",defaultValue = "5") Integer size,
                @RequestParam(value = "search",required = false) String search){
            System.out.println(search+"1");
//        在到首页之前先查询到所有动态和页码数然后发给页面遍历在页面上
            if (search==null||search==""){
                throw new CustomizeException(CustomizeErrorCode.NOI_SEARCH_NULL);
            }
            PaginationDTO pagination=questionService.searchlist(search,page,size);

            model.addAttribute("paginations",pagination);

            model.addAttribute("search",search);

            return "search";
        }

}
