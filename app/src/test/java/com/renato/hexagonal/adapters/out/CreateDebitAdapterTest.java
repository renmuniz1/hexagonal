package com.renato.hexagonal.adapters.out;

import com.renato.hexagonal.adapters.out.repository.DebitRepository;
import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import com.renato.hexagonal.adapters.out.repository.mapper.DebitEntityMapper;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = CreateDebitAdapter.class)
public class CreateDebitAdapterTest {

    @Autowired
    private CreateDebitAdapter createDebitAdapter;

    @MockitoBean
    private DebitRepository debitRepository;

    @MockitoBean
    private DebitEntityMapper debitEntityMapper;

    @Test
    void shouldCreateDebitSuccessfully() {
        // Arrange
        Debit debit = new Debit("ref-xyz", new BigDecimal("100.0"));
        DebitEntity entity = new DebitEntity(
                debit.getId(),
                debit.getEvent(),
                debit.getReference(),
                debit.getAmount(),
                debit.getCreatedAt(),
                debit.isCanceled()
        );

        when(debitEntityMapper.toDebitEntity(debit)).thenReturn(entity);

        // Act & Assert
        assertDoesNotThrow(() -> createDebitAdapter.create(debit));
        verify(debitRepository).save(entity);
    }

    @Test
    void shouldThrowInfrastructureExceptionWhenRepositoryFails() {
        // Arrange
        Debit debit = new Debit("ref-error", new BigDecimal("100.0"));
        DebitEntity entity = new DebitEntity(
                debit.getId(),
                debit.getEvent(),
                debit.getReference(),
                debit.getAmount(),
                debit.getCreatedAt(),
                debit.isCanceled()
        );

        when(debitEntityMapper.toDebitEntity(debit)).thenReturn(entity);
        doThrow(DynamoDbException.builder().message("DynamoDB Failure").build())
                .when(debitRepository).save(entity);

        // Act & Assert
        InfrastructureException ex = assertThrows(InfrastructureException.class, () -> {
            createDebitAdapter.create(debit);
        });

        assertEquals("Erro ao criar o debito", ex.getMessage());
        verify(debitRepository).save(entity);
    }



}