package com.digipay.cardservice.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {
    private long id;
    private String destination;
    private Long source;
    private int amount;
    private String date;
    private int success;
    private String description;
    private int fail;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Basic
    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "success")
    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "fail")
    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    @Basic
    @Column(name = "source")
    public Long getSource(){return source;}

    public void setSource(Long source){
        this.source = source;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source")
    private Card card;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                amount == that.amount &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(date, that.date) &&
                Objects.equals(success, that.success) &&
                Objects.equals(description, that.description) &&
                Objects.equals(fail, that.fail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, destination, amount, date, success, description, fail);
    }
}
