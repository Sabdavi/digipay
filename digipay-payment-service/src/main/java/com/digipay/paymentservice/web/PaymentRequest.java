package com.digipay.paymentservice.web;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PaymentRequest {
    private String source;
    @JsonAlias("target")
    private String dest;
    private int cvv2;
    @JsonAlias("“expire”")
    private String expDate;
    @JsonAlias("pin2")
    private int pin;
    private long amount;

    public PaymentRequest() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public int getCcv2() {
        return cvv2;
    }

    public void setCcv2(int cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
