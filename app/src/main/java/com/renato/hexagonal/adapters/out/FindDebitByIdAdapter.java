package com.renato.hexagonal.adapters.out;

import com.renato.hexagonal.adapters.out.repository.DebitRepository;
import com.renato.hexagonal.adapters.out.repository.mapper.DebitEntityMapper;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import com.renato.hexagonal.application.ports.out.FindDebitByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.time.Instant;
import java.util.Optional;

@Component
public class FindDebitByIdAdapter  implements FindDebitByIdOutputPort {

    @Autowired
    private DebitRepository debitRepository;

    @Autowired
    private DebitEntityMapper debitEntityMapper;

    @Override
    public Optional<Debit> find(String id, String event) {

        try {

            var debitEntity = debitRepository.findById(id,event);
            return debitEntity.map(entity -> debitEntityMapper.toDebit(entity));

        } catch(DynamoDbException e) {
            throw new InfrastructureException("Erro ao buscar débito", e);
        }

    }
}
