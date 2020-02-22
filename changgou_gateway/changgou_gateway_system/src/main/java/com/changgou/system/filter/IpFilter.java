package com.changgou.system.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author hql
 * @date 2019-12-31 11:23
 */
@Component
public class IpFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println(1);
        ServerHttpRequest request = exchange.getRequest();

        InetSocketAddress remoteAddress = request.getRemoteAddress();
        String hostName = remoteAddress.getHostName();
        URI uri = request.getURI();
   /*     Lock lock = new ReentrantLock();
        lock.lock();*/
        System.out.println(hostName);
        String host = uri.getHost();
        System.out.println(host);
        String path = uri.getPath();
        System.out.println(path);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
