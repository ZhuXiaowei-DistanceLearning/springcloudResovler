package com.zxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author zxw
 * @date 2019/9/6 14:41
 */
@EnableBinding
@SpringBootApplication
public class StreamServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamServerApplication.class);
    }
}
