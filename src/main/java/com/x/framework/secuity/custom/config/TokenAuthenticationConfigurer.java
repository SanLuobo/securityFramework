package com.x.framework.secuity.custom.config;

import com.x.framework.secuity.custom.filter.AuthenticationTokenFilter;
import com.x.framework.secuity.redis.util.RedisCacheUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TokenAuthenticationConfigurer extends AbstractHttpConfigurer<TokenAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) {
        RedisCacheUtil redisCacheUtil = builder.getSharedObject(ApplicationContext.class).getBean(RedisCacheUtil.class);
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter(redisCacheUtil);
        builder.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

