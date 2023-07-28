package com.x.framework.secuity.handle;

import com.x.framework.secuity.redis.util.JwtTokenUtil;
import com.x.framework.secuity.redis.util.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 登录处理
 */
public class LogoutHandler implements LogoutSuccessHandler {
    private RedisCacheUtil redisCacheUtil;

    public LogoutHandler( RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //清空用户在redis的信息
        String token = JwtTokenUtil.getToken(request);
        Claims claims = JwtTokenUtil.getClaims(token);
        String uuid = claims.get("login_user_key").toString();
        redisCacheUtil.deleteObject(uuid);
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print("退出成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
