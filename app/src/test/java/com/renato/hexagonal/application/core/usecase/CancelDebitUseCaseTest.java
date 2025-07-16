package com.renato.hexagonal.application.core.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.DebitNotFoundException;
import com.renato.hexagonal.application.ports.out.CancelDebitOutputPort;
import com.renato.hexagonal.application.ports.out.FindDebitByIdOutputPort;
import com.renato.hexagonal.application.ports.out.PublishPurchaseCancellationOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CancelDebitUseCaseTest {

    private FindDebitByIdOutputPort findDebitByIdOutputPort;
    private CancelDebitOutputPort cancelDebitOutputPort;
    private PublishPurchaseCancellationOutputPort publishCancellationOutputPort;
    private CancelDebitUseCase useCase;

    @BeforeEach
    void setUp() {
        findDebitByIdOutputPort = mock(FindDebitByIdOutputPort.class);
        cancelDebitOutputPort = mock(CancelDebitOutputPort.class);
        publishCancellationOutputPort = mock(PublishPurchaseCancellationOutputPort.class);

        useCase = new CancelDebitUseCase(
                findDebitByIdOutputPort,
                cancelDebitOutputPort,
                publishCancellationOutputPort
        );
    }

    @Test
    void shouldCancelDebitSuccessfully() throws JsonProcessingException {
        // Given
        Debit debit = new Debit("REF-001",new BigDecimal("100.0"));
        String id = debit.getId();

        when(findDebitByIdOutputPort.find(id, "REF-001")).thenReturn(Optional.of(debit));

        // When
        useCase.cancel(debit);

        // Then
        assertTrue(debit.isCanceled());
        verify(cancelDebitOutputPort).cancel(debit);
        verify(publishCancellationOutputPort).publishCancellation(debit);
    }

    @Test
    void shouldThrowExceptionWhenDebitNotFound() throws JsonProcessingException {
        // Given
        Debit debit = new Debit("REF-002", new BigDecimal("100.0"));
        String id = debit.getId();

        when(findDebitByIdOutputPort.find(id, "REF-002")).thenReturn(Optional.empty());

        // When / Then
        DebitNotFoundException exception = assertThrows(
                DebitNotFoundException.class,
                () -> useCase.cancel(debit)
        );

        assertEquals("Débito com ID " + id + " não encontrado.", exception.getMessage());
        verify(cancelDebitOutputPort, never()).cancel(any());
        verify(publishCancellationOutputPort, never()).publishCancellation(any());
    }

    @Test
    void shouldThrowExceptionWhenPublishingFails() throws JsonProcessingException {
        // Given
        Debit debit = new Debit("REF-003", new BigDecimal("100.0"));
        String id = debit.getId();

        when(findDebitByIdOutputPort.find(id, "REF-003")).thenReturn(Optional.of(debit));
        doThrow(new RuntimeException("SNS failure")).when(publishCancellationOutputPort).publishCancellation(debit);

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> useCase.cancel(debit));

        assertEquals("Erro ao publicar evento SNS", ex.getMessage());
        assertEquals("SNS failure", ex.getCause().getMessage());
        verify(cancelDebitOutputPort).cancel(debit);
        verify(publishCancellationOutputPort).publishCancellation(debit);
    }

}
