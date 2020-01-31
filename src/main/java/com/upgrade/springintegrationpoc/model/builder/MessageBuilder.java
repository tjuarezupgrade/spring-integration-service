package com.upgrade.springintegrationpoc.model.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upgrade.springintegrationpoc.model.kafkaschema.SpectrumMessage;

public interface MessageBuilder {
    SpectrumMessage build(String rabbitMqPayload) throws JsonProcessingException;
}
