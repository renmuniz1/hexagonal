package com.renato.hexagonal.application.core.usecase;

import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.ports.in.CreateDebitInputPort;
import com.renato.hexagonal.application.ports.out.CreateDebitOutputPort;

public class CreateDebitUseCase implements CreateDebitInputPort {

    private final CreateDebitOutputPort createDebitOutputPort;

    public CreateDebitUseCase(CreateDebitOutputPort createDebitOutputPort) {
        this.createDebitOutputPort = createDebitOutputPort;
    }


    @Override
    public void create(Debit debit) {

        createDebitOutputPort.create(debit);


    }
}
