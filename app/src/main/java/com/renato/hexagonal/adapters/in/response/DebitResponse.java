package com.renato.hexagonal.adapters.in.response;

import java.math.BigDecimal;

public class DebitResponse {

    public DebitResponse(String id, String event, BigDecimal amount) {
        this.id = id;
        this.event = event;
        this.amount = amount;
    }

    private String id;

    private String event;

    private BigDecimal amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
