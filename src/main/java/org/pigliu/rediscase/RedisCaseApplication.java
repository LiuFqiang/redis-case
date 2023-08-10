package org.pigliu.rediscase;

import org.pigliu.rediscase.dto.TestBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RedisCaseApplication {

    public static void main (String[] args) {
        TestBean testBean = new TestBean();
        SpringApplication.run(RedisCaseApplication.class, args);
    }

}
