package com.digipay.paymentservice.web;

public class ReportResponse {
    private long failCount;
    private long successCount;
    private String cardNumber;

    public ReportResponse(long failCount, long successCount, String cardNumber) {
        this.failCount = failCount;
        this.successCount = successCount;
        this.cardNumber = cardNumber;
    }

    public ReportResponse() {
    }

    public long getFailCount() {
      return failCount;
    }

    public void setFailCount(long failCount) {
        this.failCount = failCount;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
