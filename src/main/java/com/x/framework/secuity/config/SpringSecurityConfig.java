package com.x.framework.secuity.config;

import com.x.framework.secuity.custom.config.PasswordAuthenticationConfigurer;
import com.x.framework.secuity.custom.config.TokenAuthenticationConfigurer;
import com.x.framework.secuity.handle.AuthenticationFailHandler;
import com.x.framework.secuity.handle.LogoutHandler;
import com.x.framework.secuity.redis.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.util.StringUtils;

/**
 * 参考博客https://blog.csdn.net/u012760435/article/details/126558412
 * 核心配置类
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    private RedisCacheUtil redisCacheUtil;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests()
                //放行地址，自行指定或者使用注解声明然后aop取出放入list
                //.antMatchers(StringUtils.split(excludeUrls, ",")).permitAll()
                .antMatchers("login/**").permitAll()
                .anyRequest().authenticated().and()
                //异常处理
                .exceptionHandling().authenticationEntryPoint(new AuthenticationFailHandler()).and()
                //登出处理
                .logout().logoutUrl("/logout").logoutSuccessHandler(new LogoutHandler(redisCacheUtil)).and()
                // 务必注意这里的先后顺序，否则会报NULL异常
                //token通用接口过滤
                .apply(new TokenAuthenticationConfigurer()).and()
                //登录过滤
                .apply(new PasswordAuthenticationConfigurer()).and()
                .build();
    }

}
