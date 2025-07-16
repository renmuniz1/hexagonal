package com.renato.hexagonal.application.core.usecase;

import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.ports.out.CreateDebitOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class CreateDebitUseCaseTest {

    private CreateDebitOutputPort createDebitOutputPort;
    private CreateDebitUseCase createDebitUseCase;

    @BeforeEach
    void setUp() {
        createDebitOutputPort = mock(CreateDebitOutputPort.class);
        createDebitUseCase = new CreateDebitUseCase(createDebitOutputPort);
    }

    @Test
    void shouldCallCreateOutputPortWhenCreatingDebit() {
        // Arrange
        Debit debit = new Debit("REF-123", new BigDecimal("100.0"));

        // Act
        createDebitUseCase.create(debit);

        // Assert
        verify(createDebitOutputPort, times(1)).create(debit);


    }
}
