package com.x.framework.secuity.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class LoginUser implements UserDetails {
    private Long id;
    /**
     * 登录账号
     */
    private String loginCode;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色
     */
    private List<Role> roles;
    /**
     * 权限符
     */
    private Set<String> permissions;
    /**
     * 用户redis标识
     */
    private String token;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
