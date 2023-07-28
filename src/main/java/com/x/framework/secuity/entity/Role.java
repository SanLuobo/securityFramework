package com.x.framework.secuity.entity;

import lombok.Data;

/**
 * 对应数据库数据
 */
@Data
public class Role {
    /**
     * 角色id
     */
    private Long id;
    /**
     * 角色号
     */
    private String roleCode;
}
