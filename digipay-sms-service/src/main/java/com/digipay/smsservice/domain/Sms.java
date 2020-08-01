package com.digipay.smsservice.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sms")
public class Sms {
    private int id;
    private String message;
    private String target;
    private Byte status;

    public Sms(String message, String target, Byte status) {
        this.message = message;
        this.target = target;
        this.status = status;
    }

    public Sms() {
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "target")
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sms sms = (Sms) o;
        return id == sms.id &&
                Objects.equals(message, sms.message) &&
                Objects.equals(target, sms.target) &&
                Objects.equals(status, sms.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, target, status);
    }
}
