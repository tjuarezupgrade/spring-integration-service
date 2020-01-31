package com.upgrade.springintegrationpoc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrade.springintegrationpoc.model.builder.AccountChangeMessageBuilder;
import com.upgrade.springintegrationpoc.model.builder.MessageBuilder;
import com.upgrade.springintegrationpoc.model.builder.MonetaryTransactionMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class MessageMapperConfiguration {

    private final ObjectMapper objectMapper;

    @Bean(name = "builderMap")
    public Map<String, MessageBuilder> prepareBuilderMap() {
        return Map.ofEntries(
                Map.entry("monetaryTransaction", new MonetaryTransactionMessageBuilder(objectMapper)),
                Map.entry("accountChange", new AccountChangeMessageBuilder(objectMapper))
        );
    }

}
