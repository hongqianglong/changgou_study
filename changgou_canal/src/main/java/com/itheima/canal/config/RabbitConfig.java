package com.itheima.canal.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hql
 * @date 2020-01-05 20:50
 */
//声明式一个配置类
@Configuration
public class RabbitConfig {

       public static final String AD_UPDATE_QUEUE="ad_update_queue";
       //声明交换机
       public static final String GOOds_up_EXCHANGE="goods_up_exchange";

       //声明队列
        public static final String SEARCH_ADD_QUEUE="search_add_queue";


        @Bean
           public Queue queue(){
            return new Queue(RabbitConfig.AD_UPDATE_QUEUE);
        }
        //把队列放入容器中
        @Bean("SEARCH_ADD_QUEUE")
        public Queue SEARCH_ADD_QUEUE(){

            return  new Queue(RabbitConfig.SEARCH_ADD_QUEUE);

        }

       @Bean("GOOds_up_EXCHANGE")
    public Exchange GOOds_up_EXCHANGE(){
        return ExchangeBuilder.fanoutExchange(RabbitConfig.GOOds_up_EXCHANGE).durable(true).build();
    }

        @Bean
        //将队列绑定到交换机上
        public Binding  GOOds_up_EXCHANGE_queue(@Qualifier("SEARCH_ADD_QUEUE") Queue queue ,@Qualifier("GOOds_up_EXCHANGE") Exchange exchange){
                return BindingBuilder.bind(queue).to(exchange).with("").noargs();
        }

}
