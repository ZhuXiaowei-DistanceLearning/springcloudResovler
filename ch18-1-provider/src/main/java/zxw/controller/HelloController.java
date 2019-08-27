package zxw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2019/8/23 23:20
 */
@RestController
public class HelloController {
    @GetMapping(value = "/v1")
    public String v1() {
        return "v1";
    }

    @GetMapping(value = "/v2")
    public String v2() {
        return "v2";
    }
}
