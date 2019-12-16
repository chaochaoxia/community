package com.lic.ayr.community.dto;

import com.lic.ayr.community.model.User;
import lombok.Data;


@Data
public class QuestionToKenDTO {

    private Integer id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String tag;
    private User user;

}
