package com.system.springbootv1.restful;

import com.system.springbootv1.project.model.BaseResult;
import com.system.springbootv1.project.model.server.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: yy 2020/05/06
 **/
@Controller
@RequestMapping("/")
public class ToolApiController {

    @RequestMapping("monitor/druid/index")
    public String druid() {
        return "redirect:/druid";
    }

    @RequestMapping("monitor/actuator/index")
    public String actuator() {
        return "redirect:/actuator";
    }

    @RequestMapping("monitor/server")
    @ResponseBody
    public Object server() throws Exception {
        Server server = new Server();
        server.copyTo();
        return BaseResult.successData(server);
    }

    @RequestMapping("tool/swagger/index")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }
}
