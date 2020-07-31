package com.digipay.paymentservice.web.controller;

import com.digipay.paymentservice.service.PaymentService;
import com.digipay.paymentservice.web.PaymentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(path = "/transfer",method = RequestMethod.POST)
    public int transfer(@RequestBody PaymentRequest request){
        ResponseEntity responseEntity = paymentService.transfer(request);
        return responseEntity.getStatusCodeValue();
    }
}
