package com.renato.hexagonal.application.ports.in;

import com.renato.hexagonal.application.core.domain.Debit;

public interface CancelDebitInputPort {

    public Debit cancel(Debit debit);

}
