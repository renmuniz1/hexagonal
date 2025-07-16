package com.renato.hexagonal.application.ports.in;

import com.renato.hexagonal.application.core.domain.Debit;

import java.util.Optional;

public interface FindDebitByIdInputPort {

    public Optional<Debit> find(String debitId);

}
