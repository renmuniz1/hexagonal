package com.renato.hexagonal.config;

import com.renato.hexagonal.adapters.out.CancelDebitAdapter;
import com.renato.hexagonal.adapters.out.FindDebitByIdAdapter;
import com.renato.hexagonal.adapters.out.PublishPurchaseCancellationAdapter;
import com.renato.hexagonal.application.core.usecase.CancelDebitUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CancelDebitConfig {

    @Bean
    public CancelDebitUseCase cancelDebitUseCase(
            FindDebitByIdAdapter findDebitByIdAdapter,
            CancelDebitAdapter cancelDebitAdapter,
            PublishPurchaseCancellationAdapter publishPurchaseCancellationAdapter
    ) {
        return new CancelDebitUseCase(findDebitByIdAdapter,cancelDebitAdapter,publishPurchaseCancellationAdapter);
    }


}
