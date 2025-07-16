package com.renato.hexagonal.adapters.out;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.renato.hexagonal.application.core.domain.Debit;
import com.renato.hexagonal.application.core.exception.InfrastructureException;
import com.renato.hexagonal.application.ports.out.PublishPurchaseCancellationOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PublishPurchaseCancellationAdapter implements PublishPurchaseCancellationOutputPort {

    @Autowired
    private final SnsClient snsClient;

    @Value("${aws.sns.purchase-cancellation-topic-arn}")
    private String topicArn;



    @Override
    public void publishCancellation(Debit debit) {

        String json = "{\"evento\":\"" + debit.getEvent() + "\",\"id\":\"" + debit.getId() + "\"}";

        try {
            PublishRequest request = PublishRequest.builder()
                    .message(json)
                    .topicArn(topicArn)
                    .build();

            PublishResponse response = snsClient.publish(request);
            log.info("Evento SNS publicado. messageId={}", response.messageId());
        } catch (SnsException e) {
            throw new InfrastructureException("Erro ao publicar evento no SNS", e);
        }
    }
}
