package com.zxw.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zxw
 * @date 2019/9/5 20:36
 */
@Component
@ConfigurationProperties(prefix = "com.zxw")
public class ConfigInfoProperties {
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
