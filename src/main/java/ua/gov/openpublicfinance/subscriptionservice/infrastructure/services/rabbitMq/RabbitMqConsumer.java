package ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import ua.gov.openpublicfinance.subscriptionservice.application.events.UnsubscribeEvent;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq.messages.SubscribeMessage;
import ua.gov.openpublicfinance.subscriptionservice.application.events.NewSubscriptionReceivedEvent;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq.messages.UnsubscribeMessage;


@Component
public class RabbitMqConsumer {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RabbitMqConsumer(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RabbitListener(queues = "subscribe")
    public void receiveMessageFromSubscribe (Message<String> message) {
        String payload = message.getPayload();
        SubscribeMessage command = subscribeCommandFromPayload(payload);
        System.out.println("==========================");
        System.out.println("Received from queues 'subscribe':");
        System.out.println(command.toString());
        System.out.println("==========================");
        NewSubscriptionReceivedEvent event = new NewSubscriptionReceivedEvent(this,command);
        applicationEventPublisher.publishEvent(event);

    }
    @RabbitListener(queues = "unsubscribe")
    public void receiveMessageFromUnsubscribe(Message<String> message) {
        String payload = message.getPayload();
        UnsubscribeMessage command = unsubscribeCommandFromPayload(payload);
        System.out.println("==========================");
        System.out.println("Received from queues 'unsubscribe' <" + payload + ">");
        System.out.println("==========================");
        UnsubscribeEvent event = new UnsubscribeEvent(this,command);
        applicationEventPublisher.publishEvent(event);
    }

    private SubscribeMessage subscribeCommandFromPayload(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            SubscribeMessage command = objectMapper.readValue(json, SubscribeMessage.class);
             return command;
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new SubscribeMessage();
        }
    }
    private UnsubscribeMessage unsubscribeCommandFromPayload(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UnsubscribeMessage command = objectMapper.readValue(json, UnsubscribeMessage.class);
            return command;
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return new UnsubscribeMessage();
        }
    }
}
