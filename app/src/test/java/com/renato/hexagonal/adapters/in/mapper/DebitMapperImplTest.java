package com.renato.hexagonal.adapters.in.mapper;

import com.renato.hexagonal.adapters.in.request.DebitCancelRequest;
import com.renato.hexagonal.adapters.in.request.DebitCreateRequest;
import com.renato.hexagonal.adapters.in.response.DebitResponse;
import com.renato.hexagonal.application.core.domain.Debit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class DebitMapperImplTest {

    private DebitMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new DebitMapperImpl();
    }

    @Test
    void shouldMapDebitCreateRequestToDebit() {
        DebitCreateRequest request = new DebitCreateRequest();
        BigDecimal amount = new BigDecimal("150.50");
        request.setReference("REF123");
        request.setAmount(amount);

        Debit debit = mapper.toDebitCreate(request);

        assertNotNull(debit.getId());
        assertEquals("REF123", debit.getReference());
        assertEquals(amount, debit.getAmount());
        assertFalse(debit.isCanceled());
        assertNotNull(debit.getCreatedAt());
    }

    @Test
    void shouldMapDebitCancelRequestToDebit() {
        DebitCancelRequest request = new DebitCancelRequest();
        request.setId("abc-123");
        request.setReference("REF999");

        Debit debit = mapper.toDebitCancel(request);

        assertEquals("abc-123", debit.getId());
        assertEquals("REF999", debit.getReference());
    }

    @Test
    void shouldMapDebitToDebitResponse() {
        BigDecimal amount = new BigDecimal("150.50");
        Debit debit = new Debit("REF777", amount);
        debit.setId("xyz-789");

        DebitResponse response = mapper.toDebitResponse(debit);

        assertEquals("xyz-789", response.getId());
        assertEquals(amount, response.getAmount());

    }


}