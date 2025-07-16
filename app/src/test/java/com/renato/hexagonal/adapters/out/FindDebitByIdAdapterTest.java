package com.renato.hexagonal.adapters.out;

import com.renato.hexagonal.adapters.out.repository.DebitRepository;
import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import com.renato.hexagonal.adapters.out.repository.mapper.DebitEntityMapper;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
@TestPropertySource(properties = {
        "AWS_REGION=us-east-1",
        "AWS_ACCESS_KEY_ID=test",
        "AWS_SECRET_ACCESS_KEY=test",
        "SNS=http://localstack:4566",
        "DYNAMODB=http://localstack:4566"
})
public class FindDebitByIdAdapterTest {

    @Autowired
    private FindDebitByIdAdapter adapter;

    @MockitoBean
    private DebitRepository debitRepository;

    @MockitoBean
    private DebitEntityMapper debitEntityMapper;

    @Test
    void shouldReturnDebitWhenFound() {
        // Arrange
        String id = "abc123";
        Instant createdAt = Instant.now();
        DebitEntity entity = new DebitEntity(id, "CRIADO","ref", new BigDecimal("100.0"), createdAt, false);
        Debit expected = new Debit(id, "CRIADO","ref", new BigDecimal("100.0"), createdAt, false);

        when(debitRepository.findById(id, "ref")).thenReturn(Optional.of(entity));
        when(debitEntityMapper.toDebit(entity)).thenReturn(expected);

        // Act
        Optional<Debit> result = adapter.find(id, "ref");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected.getId(), result.get().getId());
    }

    @Test
    void shouldReturnEmptyWhenNotFound() {
        // Arrange
        String id = "notfound";
        String ref= "ref";

        when(debitRepository.findById(id, ref)).thenReturn(Optional.empty());

        // Act
        Optional<Debit> result = adapter.find(id, ref);

        // Assert
        assertTrue(result.isEmpty());
        verify(debitEntityMapper, never()).toDebit(any());
    }

    @Test
    void shouldThrowInfrastructureExceptionWhenDynamoFails() {
        // Arrange
        String id = "error-id";
        String ref= "ref";

        when(debitRepository.findById(id, ref))
                .thenThrow(DynamoDbException.builder().message("erro dynamo").build());

        // Act & Assert
        InfrastructureException exception = assertThrows(
                InfrastructureException.class,
                () -> adapter.find(id, ref)
        );

        assertEquals("Erro ao buscar débito", exception.getMessage());
    }

}