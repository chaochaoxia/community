package com.lic.ayr.community.service;

import com.lic.ayr.community.dto.PaginationDTO;
import com.lic.ayr.community.dto.QuestionToKenDTO;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
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

    public PaginationDTO list(Integer page, Integer size) {//全部

        Integer totalCount = quesstionMapper.count();

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);

        Integer offset=size*(paginationDTO.getPage()-1);

        if (totalCount>0){
            List<Question> questions = quesstionMapper.list(offset,size);//        先查全部Questions
        List<QuestionToKenDTO> questionToKenDTOList=new ArrayList<>();
        //        再遍历每一个question
        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());//每一个question的用户id creator去找对应的user

            QuestionToKenDTO questionToKenDTO = new QuestionToKenDTO();//new DTO

            BeanUtils.copyProperties(question,questionToKenDTO);//把question的属性复制给 DTO

            questionToKenDTO.setUser(user);//把user设到DTO中的user

            questionToKenDTOList.add(questionToKenDTO);//把遍历的DTO都添加到list中
        }
        paginationDTO.setQuestions(questionToKenDTOList);
    }

        return paginationDTO;//返回list
    }

    public PaginationDTO userlist(Integer userid, Integer page, Integer size) {//跟userid相关
        Integer totalCount = quesstionMapper.countByUserId(userid);
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);

        Integer offset=size*(paginationDTO.getPage()-1);
//        先查全部Questions
        List<Question> questions = quesstionMapper.userlist(userid,offset,size);
        List<QuestionToKenDTO> questionToKenDTOList=new ArrayList<>();
        //        再遍历每一个question
        for (Question question : questions) {
            User user=userMapper.findById(question.getCreator());//每一个question的用户id creator去找对应的user

            QuestionToKenDTO questionToKenDTO = new QuestionToKenDTO();//new DTO

            BeanUtils.copyProperties(question,questionToKenDTO);//把question的属性复制给 DTO

            questionToKenDTO.setUser(user);//把user设到DTO中的user

            questionToKenDTOList.add(questionToKenDTO);//把遍历的DTO都添加到list中
        }
        paginationDTO.setQuestions(questionToKenDTOList);
        return paginationDTO;//返回list
    }

    public Question getquestionById(Integer id){
        Question question = quesstionMapper.getById(id);
        return question;
    }

    public QuestionToKenDTO getById(Integer id) {
        QuestionToKenDTO questionToKenDTO = new QuestionToKenDTO();
        Question question = quesstionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        quesstionMapper.updateView(id);
        BeanUtils.copyProperties(question,questionToKenDTO);//把question的属性复制给 DTO
        User user=userMapper.findById(question.getCreator());//每一个question的用户id creator去找对应的user
        questionToKenDTO.setUser(user);
        return questionToKenDTO;
    }

    public void create(Question question) {

        if (question.getId()==null){
//
            question.setGmt_create(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_create());
            quesstionMapper.create(question);
        }else {
            question.setGmt_modified(System.currentTimeMillis());
            int updatecode=quesstionMapper.update(question);
            if (updatecode!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }

}
