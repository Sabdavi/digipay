package com.digipay.cardservice.web;

public class ReportResponse {
    private String cardNumber;
    private long successNumber;
    private long failNumber;


    public ReportResponse(String cardNumber, long successNumber, long failNumber) {
        this.cardNumber = cardNumber;
        this.successNumber = successNumber;
        this.failNumber = failNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(long successNumber) {
        this.successNumber = successNumber;
    }

    public long getFailNumber() {
        return failNumber;
    }

    public void setFailNumber(long failNumber) {
        this.failNumber = failNumber;
    }
}
