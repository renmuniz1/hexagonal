package com.renato.hexagonal.application.core.usecase;

import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.DebitNotFoundException;
import com.renato.hexagonal.application.ports.in.CancelDebitInputPort;
import com.renato.hexagonal.application.ports.out.CancelDebitOutputPort;
import com.renato.hexagonal.application.ports.out.FindDebitByIdOutputPort;
import com.renato.hexagonal.application.ports.out.PublishPurchaseCancellationOutputPort;

import java.util.Optional;

public class CancelDebitUseCase implements CancelDebitInputPort {

    private final FindDebitByIdOutputPort findDebitByIdOutputPort;

    private final CancelDebitOutputPort cancelDebitOutputPort;

    private final PublishPurchaseCancellationOutputPort publishCancellationOutputPort;

    public CancelDebitUseCase(
            FindDebitByIdOutputPort findDebitByIdOutputPort,
            CancelDebitOutputPort cancelDebitOutputPort,
            PublishPurchaseCancellationOutputPort publishCancellationOutputPort
            ) {
        this.findDebitByIdOutputPort = findDebitByIdOutputPort;
        this.cancelDebitOutputPort = cancelDebitOutputPort;
        this.publishCancellationOutputPort = publishCancellationOutputPort;
    }


    @Override
    public Debit cancel(Debit debit) {

        String id = debit.getId();
        String reference = debit.getReference();

        Optional<Debit>debitOptional = findDebitByIdOutputPort.find(id,reference);

        if(debitOptional.isEmpty()) {
            throw new DebitNotFoundException(id);
        } else {
            debit = debitOptional.get();
        }

        debit.cancel();

        cancelDebitOutputPort.cancel(debit);

        try {
            publishCancellationOutputPort.publishCancellation(debit);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar evento SNS", e);
        }
        return debit;
    }
}
