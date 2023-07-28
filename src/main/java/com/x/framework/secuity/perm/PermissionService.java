package com.x.framework.secuity.perm;

import com.x.framework.secuity.entity.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

@Service("perm")
public class PermissionService {
    private static final String ALL_PERMISSION = "*:*:*";
    public PermissionService() {
    }
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        } else {
            LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();;
            if (!ObjectUtils.isEmpty(loginUser) && !CollectionUtils.isEmpty(loginUser.getPermissions())) {
                return this.hasPermissions(loginUser.getPermissions(), permission);
            } else {
                return false;
            }
        }
    }

    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(permission);
    }
}
