package com.x.framework.secuity.config.dao;

import com.x.framework.secuity.entity.LoginUser;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    /**
     * 查询信息
     * @param username
     * @return
     */
    LoginUser loadUserByUsername(@Param("username") String username);
}
