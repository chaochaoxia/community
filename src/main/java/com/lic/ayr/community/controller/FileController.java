package com.lic.ayr.community.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.lic.ayr.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
public class FileController {



    @ResponseBody
    @RequestMapping("/file/upload")
    public FileDTO upload(HttpServletRequest request) throws IOException {

        MultipartHttpServletRequest multipartRequest= (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        InputStream inputStream = file.getInputStream();

        String[] ossfile=file.getContentType().split("/");
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId="LTAI4Fh2pYxAkw1a5ZGzmPu7";
        String accessKeySecret="l6fRMRoAvsBXYN7fY1k8DamVmzfpLB";
        String bucketName="lcyr520";
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "yourObjectName."+ossfile[ossfile.length-1];


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。

        ossClient.putObject(bucketName, objectName, inputStream);

         //关闭OSSClient
        ossClient.shutdown();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setMessage("成功");
        fileDTO.setUrl("/img/chao.jpg");
        return fileDTO;
    }
}
