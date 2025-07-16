package com.renato.hexagonal.config;

import com.renato.hexagonal.adapters.out.CreateDebitAdapter;
import com.renato.hexagonal.application.core.usecase.CreateDebitUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateDebitConfig {

    @Bean
    public CreateDebitUseCase createDebitUseCase(
            CreateDebitAdapter createDebitAdapter
    ) {
        return new CreateDebitUseCase(createDebitAdapter);

    }


}
