package com.x.framework.secuity.exception;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 系统异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public JSONObject handleException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage());
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", e.getMessage());
        res.put("data", null);
        return res;
    }

}
