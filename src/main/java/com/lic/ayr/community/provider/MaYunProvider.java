package com.lic.ayr.community.provider;


import com.alibaba.fastjson.JSON;
import com.lic.ayr.community.bean.MaYunUser;
import com.lic.ayr.community.dto.AccessTokenDTO;
import com.lic.ayr.community.dto.ReturnAccessToKenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @Component
 * 自动添加到spring容器上下文
 * */
@Component
public class MaYunProvider {


    /**
     * 用okhttp模拟post请求拿到access_token返回数据
     * */
    public ReturnAccessToKenDTO getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType  = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://gitee.com/oauth/token?grant_type=authorization_code")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
//
                ReturnAccessToKenDTO returnAccessToKenDTO = JSON.parseObject(string, ReturnAccessToKenDTO.class);

                return returnAccessToKenDTO;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    /**
     *
     * 用okhttp拿access_token 去get请求获取用户信息
     * */
    public MaYunUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://gitee.com/api/v5/user?access_token="+accessToken)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string=response.body().string();
                //JSON.parseObject(string, MaYunUser.class)自动把string json对象转换成java类对象
                MaYunUser maYunUser = JSON.parseObject(string, MaYunUser.class);
                return maYunUser;
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
    }
}
