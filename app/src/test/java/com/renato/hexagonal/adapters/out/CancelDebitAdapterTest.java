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

@SpringBootTest(classes = CancelDebitAdapter.class)
public class CancelDebitAdapterTest {

    @Autowired
    private CancelDebitAdapter cancelDebitAdapter;

    @MockitoBean
    private DebitRepository debitRepository;

    @MockitoBean
    private DebitEntityMapper debitEntityMapper;

    @Test
    void shouldCancelDebitSuccessfully() {
        // Arrange
        Debit debit = new Debit("ref-abc", new BigDecimal("100.0"));
        DebitEntity entity = new DebitEntity(debit.getId(), debit.getEvent(), debit.getReference(),debit.getAmount(), debit.getCreatedAt(), debit.isCanceled());

        when(debitEntityMapper.toDebitEntity(debit)).thenReturn(entity);

        // Act & Assert
        assertDoesNotThrow(() -> cancelDebitAdapter.cancel(debit));
        verify(debitRepository).save(entity);
    }

    @Test
    void shouldThrowInfrastructureExceptionWhenRepositoryFails() {
        // Arrange
        Debit debit = new Debit("ref-abc", new BigDecimal("100.0"));
        DebitEntity entity = new DebitEntity(debit.getId(), debit.getEvent(), debit.getReference(), debit.getAmount(), debit.getCreatedAt(), debit.isCanceled());

        when(debitEntityMapper.toDebitEntity(debit)).thenReturn(entity);
        doThrow(DynamoDbException.builder().message("Dynamo Error").build())
                .when(debitRepository).save(entity);

        // Act & Assert
        InfrastructureException exception = assertThrows(InfrastructureException.class, () -> {
            cancelDebitAdapter.cancel(debit);
        });

        assertEquals("Erro ao cancelar o debito", exception.getMessage());
        verify(debitRepository).save(entity);
    }


}