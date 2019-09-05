package com.zxw.controller;

import com.zxw.config.ConfigInfoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2019/9/5 20:38
 */
@RestController
public class ConfigClientController {
    @Autowired
    private ConfigInfoProperties configInfoProperties;

    @RequestMapping("/getConfigInfo")
    public String getConfigInfo(){
        return configInfoProperties.getConfig();
    }
}
