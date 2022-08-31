package com.example.petcatalog;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetCatalogApplication {
    private static final Logger LOG = LoggerFactory.getLogger(PetCatalogApplication.class);
    @Value("${entity.host}")
    private String host;
    @Value("${entity.port}")
    private int port;

    public static void main(String[] args) {
        LOG.info("starting the web service");
        SpringApplication.run(PetCatalogApplication.class, args);
    }

    @Bean
    public ManagedChannel createGrpcClient() {
        return ManagedChannelBuilder.forAddress(host, port)
                .build();
    }

}
