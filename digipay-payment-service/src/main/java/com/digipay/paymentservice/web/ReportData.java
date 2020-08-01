package com.digipay.paymentservice.web;

import java.util.Date;

public class ReportData {
    private Date from;
    private Date to;

    public ReportData() {
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
