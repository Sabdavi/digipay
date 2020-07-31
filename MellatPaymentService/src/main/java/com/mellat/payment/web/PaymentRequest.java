package com.mellat.payment.web;

public class PaymentRequest {
    private String source;
    private String dest;
    private int ccv2;
    private String expDate;
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
        return ccv2;
    }

    public void setCcv2(int ccv2) {
        this.ccv2 = ccv2;
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
