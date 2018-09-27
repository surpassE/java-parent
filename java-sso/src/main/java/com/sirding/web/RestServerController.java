package com.sirding.web;

import com.sirding.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2018/9/25
 */
@Controller
public class RestServerController {
    
    public final static String EXEC_CMD = "sh /share/service_switch.sh server start port node";
    
    @Autowired
    private Config config;
    
    private Map<String, String> server = new HashMap<String, String>(5){{
        put("user", "5100");   
        put("invest", "5101");   
        put("loan", "5102");   
        put("payment", "5103");   
        put("secondary", "5104");   
    }};
    
    @RequestMapping("/servers")
    @ResponseBody
    public Map<String, Object> servers(){
        Map<String, Object> result = new HashMap<>(2);
        result.put("path", config.getPath());
        result.put("node", config.getNode());
        return result;
    }

    @RequestMapping("/restart")
    @ResponseBody
    public Map<String, String> restart(String key){
        System.out.println(EXEC_CMD);
        String cmd = EXEC_CMD.replaceAll("node", config.getNode()).replaceAll("port", server.get(key)).replaceAll("server", key);
        System.out.println("======================== 执行linux命令==============================================");
        System.out.println(cmd);
        System.out.println("======================== 执行linux命令==============================================");
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.singletonMap("state", "ok");
    }
}
