package com.lic.ayr.community.dto;


import lombok.Data;

/**
 * 按照官方文档写的对应属性
 * */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String stare;

}
