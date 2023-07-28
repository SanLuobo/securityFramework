package com.x.framework.secuity.custom.filter;


import com.x.framework.secuity.entity.LoginUser;
import com.x.framework.secuity.redis.util.JwtTokenUtil;
import com.x.framework.secuity.redis.util.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AuthenticationTokenFilter extends OncePerRequestFilter {


    private final RedisCacheUtil redisCacheUtil;

    public AuthenticationTokenFilter(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenUtil.getToken(request);
        Claims claims = JwtTokenUtil.getClaims(token);
        if(StringUtils.hasText(token)&&null!=claims){
            String uuid = claims.get("login_user_key").toString();
            LoginUser user = (LoginUser) redisCacheUtil.getObject(uuid);
            if (null != user && SecurityContextHolder.getContext().getAuthentication() == null) {
                //刷新时间
                redisCacheUtil.setExpire(uuid,30, TimeUnit.MINUTES);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
