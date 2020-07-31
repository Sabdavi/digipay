package com.mellat.payment.web.controller;

import com.mellat.payment.web.PaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {
    @RequestMapping(path = "/transfer",method = RequestMethod.POST)
    public ResponseEntity doPayment(@RequestBody PaymentRequest request ){
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
