package com.x.framework.secuity.custom.provider;

import com.x.framework.secuity.config.UserDetailServiceImpl;
import com.x.framework.secuity.custom.authToken.PasswordAuthenticationToken;
import com.x.framework.secuity.entity.LoginUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailServiceImpl userDetailService;

    public PasswordAuthenticationProvider(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PasswordAuthenticationToken authToken = (PasswordAuthenticationToken) authentication;
        LoginUser userDetails =(LoginUser) userDetailService.loadUserByUsername(authToken.getLoginId());
        if (!new BCryptPasswordEncoder().matches(authToken.getCredentials(), userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误，请重新输入");
        }
        return new PasswordAuthenticationToken(userDetails, authToken.getCredentials(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
