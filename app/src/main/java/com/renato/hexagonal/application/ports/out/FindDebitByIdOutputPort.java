package com.renato.hexagonal.application.ports.out;

import com.renato.hexagonal.application.core.domain.Debit;

import java.time.Instant;
import java.util.Optional;

public interface FindDebitByIdOutputPort {

    public Optional<Debit> find(String id, String event);

}
