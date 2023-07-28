package com.x.framework.secuity.redis.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JwtTokenUtil {


    private static final String JWT_KEY = "BIhh1G5Rljp0Ma9K2SOD5vrVJgN9DJ0aNfHYmEe4tqPnNRd8nx6OVjVSpqY8UvaiHqvudmhilnqtMN14CiyWgCAS06fuaN9Of1weJNl31CbQW6FE8c8bb1JZzt8gfW9D1z4kVMs2t9MVbnj4uoOvZDAs0u3cEh8jblKcwyteT2vAGWdXe7m9lLArMKF61xFs4AzZi8YaoYieN7WIRNvBQxWUtmHJfjcZtGUYmYU3Y7TPrl9YV1lqp95FTswMpbZAThVXYGH8OIvOiAaXfrG1yb4OCsH8lLh618AvmOGrzRPI8ez3oV72WaRZeQeiqUqWCmpECcHDGXoRGTXQ27SfRzCTQ42BM3dCJIICecTMsAusdrfYpy81c5GeOeWNHuUwXcz8dgjiYMM3qdNf1hdOyoA8MGm7CEcfc9fbl7YyIpiuGiWkheWETIXedVu8AxLTCAO6V0l9aTOXwk3tvfh1KguAobM3REVdz4hbmiWNy2sryjb6rL6rD9XEIGTuwvb0J3xrGrv0Lts3XUFhW34sNBG8S4JxfGwREtb8fKMElpQciBueOUvlVmsFJmcWy4MXSlm56Hu1ASOixTvaSh55IXtU";


    /**
     * 生成JWT Token
     *
     * @param paramMap 需要写在token中的参数
     * @return token
     */
    public static String createJwtToken(Map<String,Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_KEY).compact();
        return token;
    }


    /**
     * 获取token
     *
     * @param request HttpServletRequest
     * @return token
     */
    public static String getToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            token=token.split("Bearer ")[1];
            return Objects.isNull(token) || "null".equalsIgnoreCase(token) || !StringUtils.hasText(token) ? null : token;
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }

    /**
     * 获取用户标识身份
     * @param token
     * @return
     */
    public static Claims getClaims(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.error("parse token error");
        }
        return null;
    }
}
