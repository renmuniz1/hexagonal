package com.renato.hexagonal.adapters.out.repository.mapper;

import com.renato.hexagonal.adapters.out.repository.entity.DebitEntity;
import com.renato.hexagonal.application.core.domain.Debit;


public interface DebitEntityMapper {

    DebitEntity toDebitEntity(Debit debit);

    Debit toDebit(DebitEntity debitEntity);

}
