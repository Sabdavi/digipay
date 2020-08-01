package com.digipay.paymentservice;

import com.digipay.paymentservice.messaging.JdbcKafkaTramMessageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@Import(JdbcKafkaTramMessageConfiguration.class)
public class DigipayPaymentServiceApplication {

    private int TIMEOUT = 50;
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(50)).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(DigipayPaymentServiceApplication.class, args);
    }

}
