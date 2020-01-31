package com.upgrade.springintegrationpoc.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upgrade.springintegrationpoc.model.builder.MessageBuilder;
import com.upgrade.springintegrationpoc.model.kafkaschema.SpectrumMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final Map<String, MessageBuilder> builderMap;

    public void addSchema(String messageName, MessageBuilder messageBuilder) {
        builderMap.put(messageName, messageBuilder);
    }

    public SpectrumMessage transformMessage(String messageName, String payload) throws JsonProcessingException {

        MessageBuilder builder = builderMap.get(messageName);

        if (builder == null) {
            throw new RuntimeException("No schema registered for this classname");
        }

        SpectrumMessage message = builder.build(payload);
        return message;
    }
}
