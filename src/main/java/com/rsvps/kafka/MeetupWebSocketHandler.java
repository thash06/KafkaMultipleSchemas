package com.rsvps.kafka;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class MeetupWebSocketHandler extends AbstractWebSocketHandler {

    private static final Logger logger = 
            Logger.getLogger(MeetupWebSocketHandler.class.getName());
        
    private final MeetupKafkaProducer meetupKafkaProducer;

    public MeetupWebSocketHandler(MeetupKafkaProducer meetupKafkaProducer) {
        this.meetupKafkaProducer = meetupKafkaProducer;
    }

    @Override
    public void handleMessage(WebSocketSession session,
            WebSocketMessage<?> message) {
        logger.log(Level.INFO, "New Message:\n {0}", message.getPayload());
        Gson gson = new Gson();
        Map map1 = gson.fromJson(message.getPayload().toString(), Map.class);
        logger.log(Level.INFO,"Size of map: {0}",map1.size());
        logger.log(Level.INFO,"Map keys: {0}", map1.keySet());
        logger.log(Level.INFO,"Map values: {0}", map1.values());
        logger.log(Level.INFO,message.getPayload().toString());
        meetupKafkaProducer.sendRsvpMessage(message);
    }
}
