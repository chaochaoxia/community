package com.lic.ayr.community.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {

    private Integer parent_id;
    private String content;
    private Integer type;
    private Integer commentator;

}
