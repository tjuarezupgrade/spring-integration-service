package com.upgrade.springintegrationpoc.transformer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrade.springintegrationpoc.model.MessageMapper;
import com.upgrade.springintegrationpoc.model.kafkaschema.SpectrumMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageMapperTransformer {

    private final MessageMapper messageMapper;

    private final ObjectMapper objectMapper;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MessageType {
        private String messageType;
    }

    @Transformer(inputChannel = "rawMessageChannel", outputChannel = "parsedMessageChannel")
    public SpectrumMessage parseMessage(@Payload byte[] payload, @Headers MessageHeaders header) throws JsonProcessingException {
        String parsedPayload = new String(payload, StandardCharsets.UTF_8);

        log.info("Parsing payload {}, header {}", parsedPayload, header);

        MessageType messageType = objectMapper.readValue(parsedPayload, MessageType.class);

        return messageMapper.transformMessage(messageType.getMessageType(), parsedPayload);
    }
}
