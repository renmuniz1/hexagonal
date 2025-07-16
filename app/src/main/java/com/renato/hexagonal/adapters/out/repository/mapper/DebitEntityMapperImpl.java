package com.renato.hexagonal.adapters.out.repository.mapper;

import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import com.renato.hexagonal.application.core.domain.Debit;
import org.springframework.stereotype.Component;

@Component
public class DebitEntityMapperImpl implements DebitEntityMapper{
    @Override
    public DebitEntity toDebitEntity(Debit debit) {
        return new DebitEntity(debit.getId(),debit.getEvent(),debit.getReference(),debit.getAmount(),debit.getCreatedAt(), debit.isCanceled());
    }

    @Override
    public Debit toDebit(DebitEntity debitEntity) {
        return new Debit(debitEntity.getId(),debitEntity.getEvent(),debitEntity.getReference(),debitEntity.getAmount(),debitEntity.getCreatedAt(), debitEntity.isCanceled());
    }
}
