package com.renato.hexagonal.application.ports.out;

import com.renato.hexagonal.application.core.domain.Debit;

public interface CreateDebitOutputPort {

    public void create(Debit debit);

}
