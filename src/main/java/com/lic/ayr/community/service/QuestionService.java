package com.lic.ayr.community.service;

import com.lic.ayr.community.dto.PaginationDTO;
import com.lic.ayr.community.dto.QuestionQueryDTO;
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
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuesstionMapper quesstionMapper;

    public PaginationDTO list(String search,Integer page, Integer size) {//全部


        if(search !=null){

            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));

        }


        Integer totalCount = quesstionMapper.count();

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);

        Integer offset=size*(paginationDTO.getPage()-1);
        if (totalCount>0){
            List<Question> questions = quesstionMapper.list(offset,size);//        先查全部Questions
//            List<Question> questions = quesstionMapper.selectBySearchlist(questionQueryDTO);//        先查全部Questions
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

    public PaginationDTO searchlist(String search,Integer page, Integer size) {//全部


        if(search !=null){

            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));

        }

        QuestionQueryDTO questionQueryDTO=new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);

        questionQueryDTO.setSize(size);

        Integer totalCount = quesstionMapper.countBySearch(questionQueryDTO);

        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPagination(totalCount,page,size);

        Integer offset=size*(paginationDTO.getPage()-1);
        questionQueryDTO.setPage(offset);
        if (totalCount>0){
//            List<Question> questions = quesstionMapper.list(offset,size);//        先查全部Questions
            List<Question> questions = quesstionMapper.selectBySearchlist(questionQueryDTO);//        先查全部Questions
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
        if (totalCount==0){

            throw new CustomizeException(CustomizeErrorCode.NOT_QUESTION_FOUND);
        }
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
//        增加阅读数
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

    public List<QuestionToKenDTO> selectRelated(QuestionToKenDTO questionToKenDTO) {
        if(questionToKenDTO.getTag().length()==0 ||questionToKenDTO.getTag()==null||questionToKenDTO.getTag()==""){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionToKenDTO.getTag(), ",");

        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question=new Question();
        question.setId(questionToKenDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = quesstionMapper.selsectRelated(questionToKenDTO.getId(),question.getTag());
        List<QuestionToKenDTO> questionDTOS =new ArrayList<>();
        for (Question question1 : questions) {

            QuestionToKenDTO questionDTO = new QuestionToKenDTO();//new DTO
            BeanUtils.copyProperties(question1,questionDTO);//把question的属性复制给 DTO
            questionDTOS.add(questionDTO);
        }

        return questionDTOS;

     }
}
