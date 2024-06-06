package com.marcoscsouza.DDDTP3.application.grpc;

import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import net.devh.boot.grpc.server.serverfactory.GrpcServerLifecycle;
import org.springframework.context.annotation.Bean;

public class GrpcServerConfig {

    @Bean
    public GrpcServerConfigurer grpcServerConfigurer() {
        return new GrpcServerConfigurer() {
            @Override
            public void configure(GrpcServerFactory<?> serverFactory) {
                // Custom configuration if needed
            }
        };
    }

    @Bean
    public GrpcServerLifecycle grpcServerLifecycle(GrpcServerFactory<?> serverFactory) {
        return new GrpcServerLifecycle(serverFactory);
    }
}