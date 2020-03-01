package com.lic.ayr.community.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Accessors(chain = true)
@Data
public class QuestionQueryDTO implements Serializable {

    private String search;
    private Integer page;
    private Integer size;


}
