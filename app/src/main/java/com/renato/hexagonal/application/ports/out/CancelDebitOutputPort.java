package com.renato.hexagonal.application.ports.out;

import com.renato.hexagonal.application.core.domain.Debit;

public interface CancelDebitOutputPort {

    public void cancel(Debit debit);

}
