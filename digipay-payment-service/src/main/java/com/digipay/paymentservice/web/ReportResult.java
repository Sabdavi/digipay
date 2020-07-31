package com.digipay.paymentservice.web;

public class ReportResult {
    private String cardNumber;
    private long count;

    public ReportResult(String cardNumber, long count) {
        this.cardNumber = cardNumber;
        this.count = count;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
