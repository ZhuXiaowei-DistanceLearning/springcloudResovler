package zxw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zxw
 * @date 2019/9/5 21:07
 */
@RestController
public class SleuthApplication {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/get")
    public String fromServiceA() {
        String s = restTemplate.getForObject("http://localhost:8080/service-a", String.class);
        System.out.println(s);
        return "from serviceA";
    }

}
