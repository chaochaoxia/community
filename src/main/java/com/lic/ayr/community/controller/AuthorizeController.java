package com.lic.ayr.community.controller;

import com.lic.ayr.community.bean.MaYunUser;
import com.lic.ayr.community.dto.AccessTokenDTO;
import com.lic.ayr.community.dto.ReturnAccessToKenDTO;
import com.lic.ayr.community.provider.MaYunProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 *
 * 接收code
 * */
@Controller
public class AuthorizeController {

    @Autowired
    MaYunProvider maYunProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("7a2c78014c7b2d0da69376f921bfbaa0ce3d626cd4fccd95f56149bc23504776");
        accessTokenDTO.setClient_secret("4cb3174a44f5f8d168adb86a6188331148b346fb77d1d0a6441ab1797bb4fac6");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setStare(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");

        ReturnAccessToKenDTO accessToken = maYunProvider.getAccessToken(accessTokenDTO);
        MaYunUser user = maYunProvider.getUser(accessToken.getAccess_token());
        System.out.println(user.getId()+"-----"+user.getName());
        return "index";
    }
}
