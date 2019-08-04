package org.junhi.search.listener;

import org.junhi.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author junhi
 * @date 2019/8/2 16:39
 */
@Component
public class GoodsListener {

    @Autowired
    private SearchService searchService;

    /**
     * spring根据有无异常，判断是否需要手动ack
     * 同步更新搜索索引
     * durable:持久化
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.SAVE.QUEUE", durable = "true"),  //绑定消息队列
            exchange = @Exchange(value = "LEYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),  //声明交换机
            key = {"item.insert", "item.update"}
    ))
    public void save(Long id) throws IOException {
        if(id == null){
            return;
        }
        this.searchService.save(id);
    }

    /**
     * spring根据有无异常，判断是否需要手动ack
     * 同步更新搜索索引
     * durable:持久化
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "LEYOU.SEARCH.DELETE.QUEUE", durable = "true"),  //绑定消息队列
            exchange = @Exchange(value = "LEYOU.ITEM.EXCHANGE",ignoreDeclarationExceptions = "true",type = ExchangeTypes.TOPIC),  //声明交换机
            key = {"item.delete"}
    ))
    public void delete(Long id) throws IOException {
        if(id == null){
            return;
        }
        this.searchService.delete(id);
    }

}
