package com.lic.ayr.community.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.lic.ayr.community.dto.FileDTO;
import com.lic.ayr.community.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;



@RestController
public class FileController {

    @Autowired
    FileService fileService;



    @RequestMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request) throws IOException {

        MultipartHttpServletRequest multipartRequest= (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        InputStream inputStream = file.getInputStream();
        String[] ossfile=file.getContentType().split("/");

        String URL = fileService.fileuload(inputStream, ossfile);
//
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(URL);
        return fileDTO;
    }
}
