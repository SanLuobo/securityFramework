package com.x.framework.secuity.handle;

import com.alibaba.fastjson2.JSONObject;
import com.x.framework.secuity.entity.LoginUser;
import com.x.framework.secuity.redis.util.JwtTokenUtil;
import com.x.framework.secuity.redis.util.RedisCacheUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功
 */
public class LoginSuccessHandle implements AuthenticationSuccessHandler {
    private static Integer expire=30;
    private RedisCacheUtil redisCacheUtil;

    public LoginSuccessHandle(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        //jwt放入的载荷是uuid，uuid是redis的key存储用户信息
        String token = UUID.randomUUID().toString();
        Map<String,Object> claims=new HashMap<>();
        claims.put("login_user_key",token);
        String jwtToken = JwtTokenUtil.createJwtToken(claims);
        principal.setToken(token);
        //默认30分钟过期
        redisCacheUtil.setObject(token,principal,expire, TimeUnit.MINUTES);
        //返回
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("msg", "OK");
        res.put("data", jwtToken);
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(res.toString());
    }
}
