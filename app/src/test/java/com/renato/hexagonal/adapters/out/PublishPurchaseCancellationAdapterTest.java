package com.renato.hexagonal.adapters.out;

import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublishPurchaseCancellationAdapterTest {

    @Mock
    private SnsClient snsClient;

    @InjectMocks
    private PublishPurchaseCancellationAdapter adapter;

    private final String topicArn = "arn:aws:sns:us-east-1:123456789012:purchase-cancel-topic";



    @Test
    void shouldPublishCancellationSuccessfully() {
        // Arrange
        Debit debit = new Debit("ref123", new BigDecimal("100.0"));
        debit.setId("id-1");
        PublishResponse publishResponse = PublishResponse.builder()
                .messageId("message-123")
                .build();

        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);

        // Act & Assert
        assertDoesNotThrow(() -> adapter.publishCancellation(debit));
        verify(snsClient).publish(any(PublishRequest.class));
    }

    @Test
    void shouldThrowInfrastructureExceptionWhenSnsFails() {
        // Arrange
        Debit debit = new Debit("ref123", new BigDecimal("100.0"));
        debit.setId("id-1");

        when(snsClient.publish(any(PublishRequest.class)))
                .thenThrow(SnsException.builder().message("SNS error").build());

        // Act & Assert
        InfrastructureException ex = assertThrows(
                InfrastructureException.class,
                () -> adapter.publishCancellation(debit)
        );

        assertEquals("Erro ao publicar evento no SNS", ex.getMessage());
        verify(snsClient).publish(any(PublishRequest.class));
    }


}