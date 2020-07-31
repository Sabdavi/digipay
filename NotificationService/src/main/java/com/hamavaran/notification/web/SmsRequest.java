package com.hamavaran.notification.web;

public class SmsRequest {
    private String msg;
    private String target;

    public SmsRequest(String msg, String target) {
        this.msg = msg;
        this.target = target;
    }

    public SmsRequest() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
