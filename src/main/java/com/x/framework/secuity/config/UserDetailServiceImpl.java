package com.x.framework.secuity.config;

import com.x.framework.secuity.config.dao.UserDao;
import com.x.framework.secuity.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 添加用户的密码需要加密
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser=userDao.loadUserByUsername(username);
        if(null==loginUser){
            throw new UsernameNotFoundException("用户未查询到");
        }
        Set<String> set=new HashSet<>();
        set.add("read:read");
        loginUser.setPermissions(set);
        return loginUser;
    }

    public static void main(String[] args) {
        // 密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("111111");
        //$2a$10$UWsbRm7hg1JD4s7OxD5aLuwBMfs0zlqv3k3dtpvwhWIINghJpvUji
        System.out.println(encode);
    }
}
