package com.edw.config;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.commons.marshall.ProtoStreamMarshaller;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * <pre>
 *     com.edw.config.InfinispanConfiguration
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 26 Jun 2023 10:45
 */
@Configuration
public class InfinispanConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public InfinispanRemoteCacheCustomizer remoteCacheCustomizer() {
        return b -> {
            b.remoteCache("user-cache").marshaller(ProtoStreamMarshaller.class);
        };
    }

    @Bean
    public RemoteCacheManager remoteCacheManager() {
        return new RemoteCacheManager(
                new org.infinispan.client.hotrod.configuration.ConfigurationBuilder()
                        .addServers("172.17.0.2:11222;172.17.0.3:11222;172.17.0.4:11222")
                        .security().authentication().username("admin").password("password")
                        .clientIntelligence(ClientIntelligence.HASH_DISTRIBUTION_AWARE)
                        .marshaller(ProtoStreamMarshaller.class)
                        .build());
    }
}
