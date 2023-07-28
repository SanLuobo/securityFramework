package com.x.framework.secuity.custom.authToken;

import com.x.framework.secuity.entity.LoginUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final String loginId;
    private final LoginUser principal;
    private final String credentials;

    /**
     * 登录验证
     *
     * @param loginId      用户名或手机号
     * @param credentials  MD5+SM3密码
     */
    public PasswordAuthenticationToken(String loginId, String credentials) {
        super(null);
        this.loginId = loginId;
        this.credentials = credentials;
        this.principal = null;
        this.setAuthenticated(false);
    }

    /**
     * 授权信息
     *
     * @param principal   LoginUserPojo
     * @param credentials token
     * @param authorities 角色清单
     */
    public PasswordAuthenticationToken(LoginUser principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.loginId = null;
        this.setAuthenticated(true);
    }

    public String getLoginId() {
        return loginId;
    }


    @Override
    public LoginUser getPrincipal() {
        return principal;
    }

    @Override
    public String getCredentials() {
        return credentials;
    }
}