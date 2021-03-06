package com.lic.ayr.community.model;


import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer parent_id;
    private Integer type;
    private Integer commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
    private Long comment_count;

}
