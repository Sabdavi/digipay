package com.digipay.paymentservice.web;

public class SmsRequest {
    private String msd;
    private String target;

    public SmsRequest(String msd, String target) {
        this.msd = msd;
        this.target = target;
    }

    public SmsRequest() {
    }

    public String getMsd() {
        return msd;
    }

    public void setMsd(String msd) {
        this.msd = msd;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
