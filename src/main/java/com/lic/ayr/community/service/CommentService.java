package com.lic.ayr.community.service;

import com.lic.ayr.community.dto.CommentDTO;
import com.lic.ayr.community.enums.CommentTypeEnum;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
import com.lic.ayr.community.mapper.CommentMapper;
import com.lic.ayr.community.mapper.QuesstionMapper;
import com.lic.ayr.community.model.Comment;
import com.lic.ayr.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuesstionMapper quesstionMapper;

    @Transactional
    public void insert(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(comment.getGmt_create());
        comment.setCommentator(commentDTO.getCommentator());
        if (comment.getParent_id()==null ||comment.getParent_id() == 0){
            throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);//没有选择任何问题或评论进行回复
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);//评论类型错误或不存在
        }

        if (comment.getType() ==CommentTypeEnum.COMMENT.getType()){
            //回复评论
           Comment dbcomment =commentMapper.selectById(comment.getParent_id());//根据父级id查对应的数据
            if (dbcomment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT);//您的评论不存在
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question =quesstionMapper.getById(comment.getParent_id());//根据父级id查对应的数据
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);//你找的动态不存在，换一个吧
            }
            commentMapper.insert(comment);
            quesstionMapper.updateComment(question.getId());
        }

    }
}
