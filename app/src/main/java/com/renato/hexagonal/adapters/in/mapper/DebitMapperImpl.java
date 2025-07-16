package com.renato.hexagonal.adapters.in.mapper;

import com.renato.hexagonal.adapters.in.request.DebitCancelRequest;
import com.renato.hexagonal.adapters.in.request.DebitCreateRequest;
import com.renato.hexagonal.adapters.in.response.DebitResponse;
import com.renato.hexagonal.application.core.domain.Debit;
import org.springframework.stereotype.Component;

@Component
public class DebitMapperImpl implements DebitMapper {


    @Override
    public Debit toDebitCreate(DebitCreateRequest debitRequest) {
        return new Debit(debitRequest.getReference(), debitRequest.getAmount());
    }

    @Override
    public Debit toDebitCancel(DebitCancelRequest debitRequest) {
        return new Debit(debitRequest.getId(), debitRequest.getReference());

    }

    @Override
    public DebitResponse toDebitResponse(Debit debit) {
       return new DebitResponse(debit.getId(), debit.getEvent(), debit.getAmount());
    }
}
