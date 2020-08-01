package com.digipay.paymentservice.service;

import com.digipay.paymentservice.web.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MellatProvider implements Provider {
    private RestTemplate restTemplate;
    private final String url = "http://localhost:8060/mellat-payment/payments1/transfer";

    public MellatProvider() {
        this.restTemplate = new RestTemplate();
    }


    @Override
    public ResponseEntity pay(PaymentRequest paymentRequest) {
        ResponseEntity responseEntity
                = restTemplate.postForEntity(url, paymentRequest, PaymentRequest.class);
        return responseEntity;
    }
}
