package com.changgou;

import com.changgou.util.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.goods.dao"})
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run( GoodsApplication.class);
    }
    @Value("${workId}")
    private Integer  workId;

    @Value("${datacenterId}")
    private Integer  datacenterId;


    public IdWorker idWorker(){

            return  new IdWorker(workId,datacenterId);


    }


}
