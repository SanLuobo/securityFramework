package com.x.framework.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    /**
     * 测试普通
     * @return
     */
    @GetMapping("normal")
    public Object normal(){
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("msg", "OK");
        res.put("data", "访问正常");
        return res;
    }

    /**
     * 权限通过
     * @return
     */
    @PreAuthorize("@perm.hasPermi('read:read')")
    @GetMapping("perm")
    public Object perm(){
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("msg", "OK");
        res.put("data", "有权限访问正常");
        return res;
    }

    /**
     * 权限失败
     * @return
     */
    @PreAuthorize("@perm.hasPermi('read:read:write')")
    @GetMapping("perm2")
    public Object perm2(){
        JSONObject res = new JSONObject();
        res.put("success", true);
        res.put("msg", "OK");
        res.put("data", "没法访问");
        return res;
    }
}
