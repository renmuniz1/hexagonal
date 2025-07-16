package com.renato.hexagonal.application.core.domain;

import com.renato.hexagonal.config.shared.DebitStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Debit {

    private String id;
    private String event;
    private String reference;
    private BigDecimal amount;
    private Instant createdAt;
    private boolean canceled;

    public Debit(String reference, BigDecimal amount) {
        this.id = UUID.randomUUID().toString();
        this.event = DebitStatus.DEBITO_CRIADO.getDescricao();
        this.reference = reference;
        this.amount = amount;
        this.createdAt = Instant.now();
        this.canceled = false;
    }

    public Debit(String id,String reference) {
        this.id = id;
        this.reference = reference;
    }

    public Debit(String id, String event, String reference, BigDecimal amount, Instant createdAt, boolean canceled) {
        this.id = id;
        this.event = event;
        this.reference = reference;
        this.amount = amount;
        this.createdAt = createdAt;
        this.canceled = canceled;
    }

    public void cancel() {
        if (canceled) throw new IllegalStateException("O debito ja foi cancelado.");
        this.canceled = true;
        setEvent(DebitStatus.DEBITO_CANCELADO.getDescricao());
    }

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
