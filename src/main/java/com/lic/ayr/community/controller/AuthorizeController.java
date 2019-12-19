package com.lic.ayr.community.controller;

import com.lic.ayr.community.bean.MaYunUser;
import com.lic.ayr.community.dto.AccessTokenDTO;
import com.lic.ayr.community.dto.ReturnAccessToKenDTO;
import com.lic.ayr.community.model.User;
import com.lic.ayr.community.provider.MaYunProvider;
import com.lic.ayr.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
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



    @Autowired
    UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setStare(state);
        accessTokenDTO.setRedirect_uri(redirectUrl);

        ReturnAccessToKenDTO accessToken = maYunProvider.getAccessToken(accessTokenDTO);
        MaYunUser mayunuser = maYunProvider.getUser(accessToken.getAccess_token());
        if(mayunuser!=null &&mayunuser.getId()!=null){
            String token = UUID.randomUUID().toString();

                User user = new User();
            // 没有就存入mysql数据库
                user.setToken(token);
                user.setName(mayunuser.getName());
                user.setAccount_id(String.valueOf(mayunuser.getId()));
                user.setAvatar_url(mayunuser.getAvatar_url());
                userService.createOrUpdate(user);
//            用token代替本来的cookie  把token存数据库
//            这样不会关掉服务器就要重新登录

            response.addCookie(new Cookie("token",token));
//            重定向到index页面 这样地址栏不会有多余参数
            return "redirect:/";

        }else {
            return "redirect:/";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){

        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }
}
