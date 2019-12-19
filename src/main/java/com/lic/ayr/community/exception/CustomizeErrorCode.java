package com.lic.ayr.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的动态不存在，换一个吧"),
    COMMENT_NOT_FOUND(2002,"没有选择任何问题或评论进行回复"),
    LOGIN_NOT_FOUND(2003,"没有登录"),
    SYS_NOT_FOUND(2004,"我炸了"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT(2006,"您的评论不存在");

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getcode() {
        return code;
    }
}
