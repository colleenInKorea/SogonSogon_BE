package com.sparta.sogonsogon;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@SpringBootApplication
//@EnableJpaAuditing
//@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8080", description = "Local Server URL"),@Server(url= "http://13.124.100.26:8080", description = "http서버 입니다. ")})
//public class SogonSgonApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(SogonSgonApplication.class, args);
//    }
//
//}
@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(servers = {@Server(url = "http://localhost:8080", description = "Local Server URL"),@Server(url= "https://13.124.100.26:8080", description = "http서버 입니다. ")})
public class SogonSgonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SogonSgonApplication.class, args);
    }

}