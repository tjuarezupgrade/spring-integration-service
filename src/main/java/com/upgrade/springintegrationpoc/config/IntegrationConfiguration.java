package com.upgrade.springintegrationpoc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.amqp.inbound.AmqpInboundChannelAdapter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.kafka.outbound.KafkaProducerMessageHandler;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfiguration {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spectrum.queue.name}")
    private String spectrumQueueName;

    @Value("${kafka.spectrum.topic.name}")
    private String topicName;

    @Bean
    public MessageChannel rawMessageChannel() {
        //Point-to-point channel with a single thread a round robin load balancer strategy by default.
        return new DirectChannel();
    }

    @Bean
    public MessageChannel parsedMessageChannel() {
        //Point-to-point channel with a single thread a round robin load balancer strategy by default.
        return new DirectChannel();
    }

    @Bean
    public AmqpInboundChannelAdapter inboundChannel(
            SimpleMessageListenerContainer listenerContainer, @Qualifier("rawMessageChannel") MessageChannel spectrumChannel) {

        AmqpInboundChannelAdapter adapter = new AmqpInboundChannelAdapter(listenerContainer);
        adapter.setOutputChannel(spectrumChannel);
        return adapter;
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(spectrumQueueName);
        return container;
    }

    @Bean
    @ServiceActivator(inputChannel = "parsedMessageChannel")
    public MessageHandler kafkaMessageHandler() {
        KafkaProducerMessageHandler<String, String> handler = new KafkaProducerMessageHandler<>(kafkaTemplate);
        handler.setTopicExpression(new LiteralExpression(topicName));
        handler.setMessageKeyExpression(new LiteralExpression("random-key"));
        return handler;
    }
}
