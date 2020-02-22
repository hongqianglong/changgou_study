package com.itheima.filter;

import com.itheima.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author hql
 * @date 2020-02-11 22:36
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthService authService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //先判断是否是登录操作，如果是登录操作直接放行
        if (request.getURI().getPath().equals("/api/oauth/login") ||!UrlFilter.hasAuthorize(request.getURI().getPath())){
            return chain.filter(exchange);
        }

        //在判断是否还有cookie中是否存在jti
        String jti = authService.getJti(request);
        if (StringUtils.isEmpty(jti)){
            //没有访问权限
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }


        //取出jti跟去redis里面进行查询
        String token = authService.findJti(jti);
        if (StringUtils.isEmpty(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }



         //让请求对象携带令牌信息去访问各个服务。令牌不会传递到网关
        request.mutate().header("Authorization","Bearer "+token);
        return chain.filter(exchange);


    }
    //这个是过滤器的执行顺序，数字越小越先执行
    @Override
    public int getOrder() {
        return 0;
    }
}
