package com.digipay.paymentservice.service;

import com.digipay.paymentservice.web.PaymentRequest;
import org.springframework.http.ResponseEntity;

public interface Provider {
    ResponseEntity pay(PaymentRequest paymentRequest);
}
