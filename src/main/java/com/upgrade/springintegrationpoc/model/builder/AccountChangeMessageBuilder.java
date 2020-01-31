package com.upgrade.springintegrationpoc.model.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrade.springintegrationpoc.model.kafkaschema.AccountChangeMessage;
import com.upgrade.springintegrationpoc.model.kafkaschema.SpectrumMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountChangeMessageBuilder implements MessageBuilder {

    private final ObjectMapper objectMapper;

    @Override
    public SpectrumMessage build(String payload) throws JsonProcessingException {
        return objectMapper.readValue(payload, AccountChangeMessage.class);
    }
}

