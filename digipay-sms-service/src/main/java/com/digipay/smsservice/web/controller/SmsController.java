package com.digipay.smsservice.web.controller;

import com.digipay.smsservice.web.SmsRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/messages")
public class SmsController {

    @RequestMapping(path = "/send-sms",method = RequestMethod.POST)
    public ResponseEntity sentSms(@RequestBody SmsRequest request){
        System.out.println("message::"+request.getMsd()+"sent to::"+request.getTarget());
        return new ResponseEntity(HttpStatus.OK);
    }
}
