package com.x.framework.secuity.custom.config;

import com.x.framework.secuity.config.UserDetailServiceImpl;
import com.x.framework.secuity.custom.filter.AuthenticationTokenFilter;
import com.x.framework.secuity.custom.filter.PasswordAuthenticationFilter;
import com.x.framework.secuity.custom.provider.PasswordAuthenticationProvider;
import com.x.framework.secuity.handle.LoginFailHandle;
import com.x.framework.secuity.handle.LoginSuccessHandle;
import com.x.framework.secuity.redis.util.RedisCacheUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PasswordAuthenticationConfigurer extends AbstractHttpConfigurer<PasswordAuthenticationConfigurer, HttpSecurity> {
    @Override
    public void configure(HttpSecurity builder) {
        // 拦截 POST /login 请求
        RequestMatcher matcher = new AntPathRequestMatcher("/login", "POST", true);

        UserDetailServiceImpl userDetailService = builder.getSharedObject(ApplicationContext.class).getBean(UserDetailServiceImpl.class);
        RedisCacheUtil redisCacheUtil = builder.getSharedObject(ApplicationContext.class).getBean(RedisCacheUtil.class);
        AuthenticationManager localAuthManager = builder.getSharedObject(AuthenticationManager.class);

        PasswordAuthenticationFilter filter = new PasswordAuthenticationFilter(matcher, localAuthManager);
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandle(redisCacheUtil));
        filter.setAuthenticationFailureHandler(new LoginFailHandle());

        // 务必注意这里与配置类中声明的先后顺序
        builder.authenticationProvider(new PasswordAuthenticationProvider(userDetailService))
                .addFilterBefore(filter, AuthenticationTokenFilter.class);
    }
}

