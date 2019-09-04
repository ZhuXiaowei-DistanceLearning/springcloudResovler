package com.zxw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * @author zxw
 * @date 2019/8/26 21:46
 */
@SpringBootApplication
@ImportResource(locations = { "classpath:applicationContext.xml" })
public class JobApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(JobApplication.class).web(false).run(args);
        try {
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
        }
    }
}
