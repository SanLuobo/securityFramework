package com.x.framework.secuity.custom.filter;

import com.alibaba.fastjson2.JSONObject;
import com.x.framework.secuity.custom.authToken.PasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import com.x.framework.secuity.util.HttpRequestUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public PasswordAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        JSONObject params = JSONObject.parseObject(HttpRequestUtil.getBodyString(request));
        Authentication authentication = new PasswordAuthenticationToken(params.getString("loginKey"), params.getString("password"));
        return this.getAuthenticationManager().authenticate(authentication);
    }
}

