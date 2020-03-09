package com.system.springbootv1.controller;

import com.system.springbootv1.service.RabbitMQService;
import com.system.springbootv1.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: yy 2020/02/25
 **/
@Controller
@RequestMapping("/mq/*")
public class RabbitMQController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @RequestMapping("send")
    @ResponseBody
    public void sendMsg(HttpServletRequest request) {
        String message = ServletUtils.getParameter("message");
        rabbitMQService.send(message);
    }
}
