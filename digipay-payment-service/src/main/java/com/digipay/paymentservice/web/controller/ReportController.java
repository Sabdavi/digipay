package com.digipay.paymentservice.web.controller;

import com.digipay.paymentservice.service.PaymentService;
import com.digipay.paymentservice.web.ReportResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/transactions")
public class ReportController {

    private PaymentService paymentService;

    public ReportController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ReportResponse> getTransactions(){
        return paymentService.getTransactions();
    }
}
