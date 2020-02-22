package com.itheima.canal.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.itheima.canal.config.RabbitConfig;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hql
 * @date 2020-01-05 20:02
 */
//声明当前的类式监听类
@CanalEventListener
public class TestListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ListenPoint(schema = "changgou_business",table = "tb_ad")
    public  void add(CanalEntry.EventType eventType,CanalEntry.RowData rowData) {
/*        System.out.println("广告表发生改变");
        rowData.getBeforeColumnsList().forEach((c)-> System.out.println("改变前的数据"+c.getName()+":"+c.getValue()));


        rowData.getAfterColumnsList().forEach((c)-> System.out.println("改变后的数据"+c.getName()+":"+c.getValue()));
    }
        */
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            if ("position".equals(column.getName())){
                rabbitTemplate.convertAndSend("",RabbitConfig.AD_UPDATE_QUEUE,column.getValue());
            }
        }

    }
}
