package com.zxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;

/**
 * @author zxw
 * @date 2019/8/21 21:24
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGatewayApplication {
    //    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes().route(r -> r.path("/jd").uri("http://jd.com:80/").id("jd_route")).build();
//    }
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        ZonedDateTime dateTime = LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault());
//        return builder.routes().route("before_route", r -> r.before(dateTime).uri("http://baidu.com")).build();
//    }
//
//    public static void main(String[] args) {
//        Executors.newFixedThreadPool()
//        SpringApplication.run(SpringCloudGatewayApplication.class);
//    }
}
