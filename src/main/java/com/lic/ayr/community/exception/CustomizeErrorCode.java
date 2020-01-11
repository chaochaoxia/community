package com.lic.ayr.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"你找的动态不存在，换一个吧"),
    COMMENT_NOT_FOUND(2002,"没有选择任何问题或评论进行回复"),
    LOGIN_NOT_FOUND(2003,"你还没有登录,请登录后重试"),
    SYS_NOT_FOUND(2004,"我炸了"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT(2006,"您的评论不存在"),
    COMMENT_IS_EMPTY(2007,"回复内容不能为空！");

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
