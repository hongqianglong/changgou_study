package com.changgou.business.listern;

import okhttp3.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;



/**
 * @author hql
 * @date 2020-01-05 21:02
 */
@Component
public class AddListern {

    @RabbitListener(queues = {"ad_update_queue"})
        public void getMessage(String message){
        System.out.println("接受到的信息为"+message);
        //使用okhttpclient 调用
        OkHttpClient okHttpClient= new OkHttpClient();
        //  将接受的信息发送request请求到nginx中
        String url= "http://47.111.236.92/ad_update?position="+message;
        Request request =  new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //如果失败的话
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                System.out.println("请求成功："+response.message());
            }
        });



    }

}
