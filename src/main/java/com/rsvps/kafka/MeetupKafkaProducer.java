package com.rsvps.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;

import java.util.logging.Logger;

@Component
@EnableBinding(Source.class)
public class MeetupKafkaProducer {
    private static final Logger logger = Logger.getLogger(MeetupKafkaProducer.class.getName());
    private static final int SENDING_MESSAGE_TIMEOUT_MS = 10000;

    private final Source source;

    public MeetupKafkaProducer(Source source) {
        this.source = source;
    }

    public void sendRsvpMessage(WebSocketMessage<?> message) {
        //logger.info(" Message Payload: \n" +  message.getPayload());
        source.output()
                .send(MessageBuilder.withPayload(message.getPayload())
                        .build(),
                        SENDING_MESSAGE_TIMEOUT_MS);   
    }
}