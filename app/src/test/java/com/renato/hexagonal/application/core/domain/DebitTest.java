package com.renato.hexagonal.application.core.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class DebitTest {

    @Test
    void shouldCreateDebitWithCorrectValues() {
        String reference = "REF-001";
        BigDecimal amount = new BigDecimal("100.0");

        Debit debit = new Debit(reference, amount);

        assertNotNull(debit.getId(), "ID should not be null");
        assertEquals(reference, debit.getReference());
        assertEquals(amount, debit.getAmount());
        assertNotNull(debit.getCreatedAt(), "Creation timestamp should not be null");
        assertFalse(debit.isCanceled(), "Debit should not be canceled initially");
    }

    @Test
    void shouldAllowFieldUpdates() {
        Debit debit = new Debit("REF-OLD", new BigDecimal("100.0"));

        String newId = "custom-id-123";
        String newReference = "REF-UPDATED";
        BigDecimal newAmount = new BigDecimal("100.0");
        Instant newCreatedAt = Instant.parse("2024-01-01T12:00:00Z");

        debit.setId(newId);
        debit.setEvent(newReference);
        debit.setAmount(newAmount);
        debit.setCreatedAt(newCreatedAt);
        debit.setCanceled(true);

        assertEquals(newId, debit.getId());
        assertEquals(newReference, debit.getEvent());
        assertEquals(newAmount, debit.getAmount());
        assertEquals(newCreatedAt, debit.getCreatedAt());
        assertTrue(debit.isCanceled());
    }

    @Test
    void shouldCancelDebitSuccessfully() {
        Debit debit = new Debit("REF-002", new BigDecimal("100.0"));

        debit.cancel();

        assertTrue(debit.isCanceled(), "Debit should be canceled after calling cancel()");
    }

    @Test
    void shouldThrowExceptionWhenCancelingAlreadyCanceledDebit() {
        Debit debit = new Debit("REF-003", new BigDecimal("100.0"));
        debit.cancel();

        IllegalStateException exception = assertThrows(IllegalStateException.class, debit::cancel);
        assertEquals("O debito ja foi cancelado.", exception.getMessage());
    }

}
