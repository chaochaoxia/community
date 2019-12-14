package com.lic.ayr.community.controller;

import com.lic.ayr.community.bean.MaYunUser;
import com.lic.ayr.community.dto.AccessTokenDTO;
import com.lic.ayr.community.dto.ReturnAccessToKenDTO;
import com.lic.ayr.community.provider.MaYunProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${MaYun.client.id}")
    private String clientId;

    @Value("${MaYun.client.secret}")
    private String clientSecret;

    @Value("${MaYun.redirect.uri}")
    private String redirectUrl;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setStare(state);
        accessTokenDTO.setRedirect_uri(redirectUrl);

        ReturnAccessToKenDTO accessToken = maYunProvider.getAccessToken(accessTokenDTO);
        MaYunUser user = maYunProvider.getUser(accessToken.getAccess_token());
        System.out.println(user.getId()+"-----"+user.getName());
        return "index";
    }
}
