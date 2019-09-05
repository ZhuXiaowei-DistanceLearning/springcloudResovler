package com.zxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author zxw
 * @date 2019/9/5 20:01
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigGitApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigGitApplication.class);
    }
}
