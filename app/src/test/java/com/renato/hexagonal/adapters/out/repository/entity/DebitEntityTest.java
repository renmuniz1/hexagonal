package com.renato.hexagonal.adapters.out.repository.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class DebitEntityTest {

    @Test
    void shouldCreateAndSetDebitEntityFieldsCorrectly() {
        String id = "debit-123";
        String reference = "REF-456";
        BigDecimal amount = new BigDecimal("150.50");
        Instant createdAt = Instant.now();
        boolean canceled = true;

        DebitEntity entity = new DebitEntity();
        entity.setId(id);
        entity.setEvent(reference);
        entity.setAmount(amount);
        entity.setCreatedAt(createdAt);
        entity.setCanceled(canceled);

        assertEquals(id, entity.getId());
        assertEquals(reference, entity.getEvent());
        assertEquals(amount, entity.getAmount());
        assertEquals(createdAt, entity.getCreatedAt());
        assertTrue(entity.isCanceled());
    }

    @Test
    void shouldCreateDebitEntityWithAllArgsConstructor() {
        String id = "debit-999";
        String event = "CRIADO";
        String reference = "REF-777";
        BigDecimal amount = new BigDecimal("150.50");
        Instant createdAt = Instant.now();
        boolean canceled = false;

        DebitEntity entity = new DebitEntity(id, event, reference, amount, createdAt, canceled);

        assertEquals(id, entity.getId());
        assertEquals(event, entity.getEvent());
        assertEquals(reference, entity.getReference());
        assertEquals(amount, entity.getAmount());
        assertEquals(createdAt, entity.getCreatedAt());
        assertFalse(entity.isCanceled());
    }

    @Test
    void shouldCreateDebitEntityWithNoArgsConstructor() {
        DebitEntity entity = new DebitEntity();

        assertNull(entity.getId());
        assertNull(entity.getEvent());
        assertNull(entity.getCreatedAt());
        assertFalse(entity.isCanceled());
    }

}