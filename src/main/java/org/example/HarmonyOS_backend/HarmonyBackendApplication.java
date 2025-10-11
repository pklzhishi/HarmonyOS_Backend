package org.example.HarmonyOS_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.HarmonyOS_backend.mapper")
@SpringBootApplication
public class HarmonyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(HarmonyBackendApplication.class, args);
    }

}
