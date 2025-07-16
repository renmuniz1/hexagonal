package com.renato.hexagonal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;

@Configuration
public class PublishPurchaseCancellationConfig {

    @Value("${cloud.aws.endpoint.sns}")
    private String snsEndpoint;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .endpointOverride(URI.create(snsEndpoint))
                .region(Region.of(region))
                .build();
    }

}
