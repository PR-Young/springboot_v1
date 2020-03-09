package com.system.springbootv1.controller;

import com.system.springbootv1.common.kafka.KafkaProducer;
import com.system.springbootv1.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: yy 2020/02/26
 **/
@Controller
@RequestMapping("/kafka/*")
public class KafkaController {

    @Autowired
    private KafkaProducer producer;

    @RequestMapping("send")
    @ResponseBody
    public void send(HttpServletRequest request) {
        String message = ServletUtils.getParameter("message");
        producer.send(message);
    }
}
