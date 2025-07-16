package com.renato.hexagonal.adapters.out.repository;

import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.util.Optional;

@Repository
public class DebitRepository {

    private final DynamoDbTable<DebitEntity> table;


    public DebitRepository(DynamoDbTable<DebitEntity> table) {

        this.table = table;
    }

    public void save(DebitEntity debit) {
        table.putItem(debit);
    }

    public Optional<DebitEntity> findById(String id, String reference) {

        DebitEntity key = new DebitEntity();
        key.setId(id);
        key.setReference(reference);

        return Optional.ofNullable(table.getItem(key));

    }

}
