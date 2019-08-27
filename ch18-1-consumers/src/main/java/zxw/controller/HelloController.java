package zxw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxw
 * @date 2019/8/23 23:20
 */
@RestController
public class HelloController {
    @GetMapping("/{name}")
    public String hello(@PathVariable("name") String name) {
        return "Hello, " + name + "!";
    }
}
