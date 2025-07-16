package com.renato.hexagonal.adapters.out.repository;

import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DebitRepositoryTest {

    private DynamoDbTable<DebitEntity> table;
    private DebitRepository repository;

    @BeforeEach
    void setup() {
        table = mock(DynamoDbTable.class);
        repository = new DebitRepository(table);
    }

    @Test
    void testSave() {
        DebitEntity debit = new DebitEntity();
        debit.setId("123");
        debit.setReference("ref-1");

        repository.save(debit);

        verify(table, times(1)).putItem(debit);
    }

    @Test
    void testFindByIdFound() {
        DebitEntity expected = new DebitEntity();
        expected.setId("123");
        expected.setReference("ref-1");

        when(table.getItem(any(DebitEntity.class))).thenReturn(expected);

        Optional<DebitEntity> result = repository.findById("123", "ref-1");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getId());
        assertEquals("ref-1", result.get().getReference());

        ArgumentCaptor<DebitEntity> captor = ArgumentCaptor.forClass(DebitEntity.class);
        verify(table).getItem(captor.capture());

        assertEquals("123", captor.getValue().getId());
        assertEquals("ref-1", captor.getValue().getReference());
    }

    @Test
    void testFindByIdNotFound() {
        when(table.getItem(any(DebitEntity.class))).thenReturn(null);

        Optional<DebitEntity> result = repository.findById("999", "ref-x");

        assertFalse(result.isPresent());
    }

}