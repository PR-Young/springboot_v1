package com.system.springbootv1.common.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: yy 2020/02/25
 **/
//@Configuration
public class RabbitMQConsumer {

    private Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "testQueue", containerFactory = "single")
    public void consumer(String message) {
        try {
            String msg = new String(message);
            logger.info("监听到消息： {} ", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
