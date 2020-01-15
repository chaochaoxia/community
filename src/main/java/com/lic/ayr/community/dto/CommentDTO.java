package com.lic.ayr.community.dto;

import com.lic.ayr.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parent_id;
    private Integer type;
    private Integer commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
    private Long comment_count;
    private User user;
}
