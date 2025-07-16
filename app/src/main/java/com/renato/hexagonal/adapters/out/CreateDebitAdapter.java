package com.renato.hexagonal.adapters.out;

import com.renato.hexagonal.adapters.out.repository.DebitRepository;
import com.renato.hexagonal.adapters.out.repository.mapper.DebitEntityMapper;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import com.renato.hexagonal.application.ports.out.CreateDebitOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Component
public class CreateDebitAdapter implements CreateDebitOutputPort {

    @Autowired
    private DebitRepository debitRepository;

    @Autowired
    private DebitEntityMapper debitEntityMapper;


    @Override
    public void create(Debit debit) {
        var debitEntity = debitEntityMapper.toDebitEntity(debit);

        try {
            debitRepository.save(debitEntity);
        } catch(DynamoDbException e) {
            throw new InfrastructureException("Erro ao criar o debito", e);
        }

    }
}
