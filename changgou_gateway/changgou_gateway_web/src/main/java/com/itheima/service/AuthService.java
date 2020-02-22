package com.itheima.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

/**
 * @author hql
 * @date 2020-02-12 9:30
 */
@Service
public class AuthService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public  String findJti(String jti) {
        String token= stringRedisTemplate.boundValueOps(jti).get();
        System.out.println(token);
        if (token!=null){
            return token;
        }
        return null;
    }

    public String getJti(ServerHttpRequest request) {
        HttpCookie uid = request.getCookies().getFirst("uid");
        if (uid!=null){
            String token = uid.getValue();
            return  token;
        }
        return null;
    }
}
