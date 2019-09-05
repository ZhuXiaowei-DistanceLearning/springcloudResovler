package zxw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author zxw
 * @date 2019/9/5 21:07
 */
@SpringBootApplication
public class SleuthApplicationB {
    public static void main(String[] args) {
        SpringApplication.run(SleuthApplicationB.class);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
