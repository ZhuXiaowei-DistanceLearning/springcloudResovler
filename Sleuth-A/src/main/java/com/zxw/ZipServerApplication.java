package com.zxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * @author zxw
 * @date 2019/9/5 21:34
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZipServerApplication.class);
    }
}
