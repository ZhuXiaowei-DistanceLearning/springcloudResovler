package com.zxw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2019/9/5 21:07
 */
@RestController
public class SleuthApplication {

    @GetMapping("/service-a")
    public String fromServiceA() {
        return "from serviceA";
    }

}
