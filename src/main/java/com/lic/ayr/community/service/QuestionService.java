package com.lic.ayr.community.service;

import com.lic.ayr.community.dto.QuestionToKenDTO;
import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.mapper.UserMapper;
import com.lic.ayr.community.model.Question;
import com.lic.ayr.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuesstionMapper quesstionMapper;

    public List<QuestionToKenDTO> list() {
//        先查全部Questions
        List<Question> questions = quesstionMapper.list();
        List<QuestionToKenDTO> questionToKenDTOList=new ArrayList<>();
//        再遍历每一个question
        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());//每一个question的用户id creator去找对应的user

            QuestionToKenDTO questionToKenDTO = new QuestionToKenDTO();//new DTO

            BeanUtils.copyProperties(question,questionToKenDTO);//把question的属性复制给 DTO

            questionToKenDTO.setUser(user);//把user设到DTO中的user

            questionToKenDTOList.add(questionToKenDTO);//把遍历的DTO都添加到list中
        }
            return questionToKenDTOList;//返回list
    }

}
