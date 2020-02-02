package com.lic.ayr.community.service;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {

    @Value("${aliyun.OSS.endpoint}")
    private String endpoint;

    @Value("${aliyun.OSS.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.OSS.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.OSS.bucketName}")
    private String bucketName;




    public String fileuload(InputStream inputStream,String[] ossfile){


        //        有效时间

        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);

        // ObjectName上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = UUID.randomUUID()+"." +ossfile[ossfile.length-1];

        ObjectMetadata objectMetadata=new ObjectMetadata();
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。

        objectMetadata.setContentType("image/jpg");
        ossClient.putObject(bucketName, objectName, inputStream,objectMetadata);
        URL url = ossClient.generatePresignedUrl(bucketName, objectName,expiration);
        String URL=url.toString();

        //关闭OSSClient
        ossClient.shutdown();
        return URL;
    }
}
