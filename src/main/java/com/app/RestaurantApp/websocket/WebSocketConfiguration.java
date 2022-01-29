package com.app.RestaurantApp.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    // http://localhost:8080/socket/  i ovo koristimo da se klijenti povezu sa serverom
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    // http://localhost:8080/socket-subscriber/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/socket-subscriber")
                // Prefiks koji koji se koristi za mapiranje svih poruka.
                // Klijenti moraju da ga navedu kada salju poruku serveru.
                // Svaki URL bi pocinjao ovako: http://localhost:8080/socket-subscriber/…/…
                .enableSimpleBroker("/socket-publisher");
        // Definisanje topic-a (ruta) na koje klijenti mogu da se pretplate.
        // SimpleBroker cuva poruke u memoriji i salje ih klijentima na definisane topic-e.
        // Server kada salje poruke, salje ih na rute koje su ovde definisane, a klijenti cekaju na poruke.
        // Vise ruta odvajamo zarezom, npr. enableSimpleBroker("/ruta1", "/ruta2");
    }
}
