package com.lic.ayr.community.advice;

import com.alibaba.fastjson.JSON;
import com.lic.ayr.community.Util.LicReturn;
import com.lic.ayr.community.exception.CustomizeErrorCode;
import com.lic.ayr.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@ControllerAdvice
public class CustomizeExceptionHandler {
    //错误页面处理 异常拦截器
    @ExceptionHandler(Exception.class)
    ModelAndView hanle(Throwable ex, Model model, HttpServletRequest request, HttpServletResponse  response){

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)){
            LicReturn licReturn;
            //返回json
            if (ex instanceof CustomizeException){
                licReturn= LicReturn.errof((CustomizeException) ex);

            }else {//处理不了的时候
                licReturn= LicReturn.errof(CustomizeErrorCode.SYS_NOT_FOUND);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(licReturn));
                writer.close();
            }catch (Exception e){

            }
            return null;

        }else {
            if (ex instanceof CustomizeException){
                model.addAttribute("messages",ex.getMessage());

            }else {//处理不了的时候
                model.addAttribute("messages",CustomizeErrorCode.SYS_NOT_FOUND);
            }

            return new ModelAndView("error");
        }


    }

}
