package com.renato.hexagonal.adapters.in.mapper;

import com.renato.hexagonal.adapters.in.request.DebitCancelRequest;
import com.renato.hexagonal.adapters.in.request.DebitCreateRequest;
import com.renato.hexagonal.adapters.in.response.DebitResponse;
import com.renato.hexagonal.application.core.domain.Debit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


public interface DebitMapper {

    Debit toDebitCreate(DebitCreateRequest debitRequest);

    Debit toDebitCancel(DebitCancelRequest debitRequest);

    DebitResponse toDebitResponse(Debit debit);

}
