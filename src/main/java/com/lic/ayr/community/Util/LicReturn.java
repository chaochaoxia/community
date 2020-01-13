package com.lic.ayr.community.Util;

import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
import lombok.Data;

@Data
public class LicReturn<T> {

    private Integer code;
    private String message;
    private T data;

    public static LicReturn errof(Integer code,String message){
        LicReturn licReturn=new LicReturn();
        licReturn.setCode(code);
        licReturn.setMessage(message);
        return licReturn;
    }

    public static LicReturn errof(CustomizeErrorCode customizeErrorCode){

        return errof(customizeErrorCode.getcode(),customizeErrorCode.getMessage());
    }

    public static LicReturn errof(CustomizeException ex){

        return errof(ex.getCode(),ex.getMessage());
    }

    public static LicReturn okof(){

        LicReturn licReturn = new LicReturn();
        licReturn.setCode(200);
        licReturn.setMessage("成功");
        return licReturn;
    }

    public static LicReturn okof(Object t){

        LicReturn licReturn = new LicReturn();
        licReturn.setCode(200);
        licReturn.setMessage("成功");
        licReturn.setData(t);
        return licReturn;
    }
}
