package com.rsvps;

import com.rsvps.kafka.MeetupWebSocketHandler;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@SpringBootApplication
public class KafkaProducerApplication {
    private static final String MEETUP_RSVPS_ENDPOINT = "ws://stream.meetup.com/2/rsvps";
    private static final String MEETUP_PHOTOS_ENDPOINT = "ws://stream.meetup.com/2/photos";
    private static final String EVENT_COMMENTS_ENDPOINT = "ws://stream.meetup.com/2/event_comments";

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApplication.class, args);
    }

    @Bean
    public ApplicationRunner initializeConnection(
            MeetupWebSocketHandler meetupWebSocketHandler) {
            return args -> {
                WebSocketClient rsvpsSocketClient = new StandardWebSocketClient();

                rsvpsSocketClient.doHandshake(
                        meetupWebSocketHandler, MEETUP_RSVPS_ENDPOINT);

                rsvpsSocketClient.doHandshake(
                        meetupWebSocketHandler, EVENT_COMMENTS_ENDPOINT);
                rsvpsSocketClient.doHandshake(
                        meetupWebSocketHandler, MEETUP_PHOTOS_ENDPOINT);
            };
        }
}
