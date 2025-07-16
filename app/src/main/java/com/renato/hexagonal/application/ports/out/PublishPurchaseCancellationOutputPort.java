package com.renato.hexagonal.application.ports.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.renato.hexagonal.application.core.domain.Debit;

public interface PublishPurchaseCancellationOutputPort {

    public void publishCancellation(Debit debit) throws JsonProcessingException;

}
