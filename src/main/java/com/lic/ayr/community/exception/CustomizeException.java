package com.lic.ayr.community.exception;

public class CustomizeException extends RuntimeException {

    private String message;
    private Integer code;



    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message=errorCode.getMessage();
        this.code=errorCode.getcode();
    }
    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
