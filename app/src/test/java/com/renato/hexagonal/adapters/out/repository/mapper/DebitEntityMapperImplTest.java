package com.renato.hexagonal.adapters.out.repository.mapper;

import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import com.renato.hexagonal.application.core.domain.Debit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class DebitEntityMapperImplTest {

    private DebitEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new DebitEntityMapperImpl();
    }

    @Test
    void testToDebitEntity() {
        Debit debit = new Debit(
                "123",
                "CRIADO",
                "ref001",
                new BigDecimal("150.0"),
                Instant.parse("2025-07-14T12:00:00Z"),
                false
        );

        DebitEntity entity = mapper.toDebitEntity(debit);

        assertNotNull(entity);
        assertEquals(debit.getId(), entity.getId());
        assertEquals(debit.getEvent(), entity.getEvent());
        assertEquals(debit.getAmount(), entity.getAmount());
        assertEquals(debit.getCreatedAt(), entity.getCreatedAt());
        assertEquals(debit.isCanceled(), entity.isCanceled());
    }

    @Test
    void testToDebit() {
        DebitEntity entity = new DebitEntity(
                "456",
                "CRIADO",
                "ref002",
                new BigDecimal("300.0"),
                Instant.parse("2025-07-15T10:30:00Z"),
                true
        );

        Debit debit = mapper.toDebit(entity);

        assertNotNull(debit);
        assertEquals(entity.getId(), debit.getId());
        assertEquals(entity.getEvent(), debit.getEvent());
        assertEquals(entity.getAmount(), debit.getAmount());
        assertEquals(entity.getCreatedAt(), debit.getCreatedAt());
        assertEquals(entity.isCanceled(), debit.isCanceled());
    }

}