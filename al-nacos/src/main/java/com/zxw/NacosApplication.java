package com.zxw;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zxw
 * @date 2019/9/4 20:35
 */
@NacosPropertySource(dataId = "example", autoRefreshed = true)
@SpringBootApplication
public class NacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class);
    }
}
