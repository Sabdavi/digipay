package com.digipay.smsservice;

import com.digipay.smsservice.messaging.JdbcKafkaTramMessageConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@Import(JdbcKafkaTramMessageConfiguration.class)
public class DigipaySmsServiceApplication {

    private int TIMEOUT = 50;
    public static void main(String[] args) {
        SpringApplication.run(DigipaySmsServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(50)).build();
    }


}
