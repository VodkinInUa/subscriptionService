package ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventPublisher.class);

    public RabbitMqConsumer(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RabbitListener(queues = "subscribe")
    public void receiveMessageFromSubscribe (Message<String> message) {
        String payload = message.getPayload();
        logger.info("Received from queues 'subscribe':" + payload);
        SubscribeMessage command = subscribeCommandFromPayload(payload);
        NewSubscriptionReceivedEvent event = new NewSubscriptionReceivedEvent(this,command);
        applicationEventPublisher.publishEvent(event);

    }
    @RabbitListener(queues = "unsubscribe")
    public void receiveMessageFromUnsubscribe(Message<String> message) {
        String payload = message.getPayload();
        logger.info("Received from queues 'unsubscribe':" + payload);
        UnsubscribeMessage command = unsubscribeCommandFromPayload(payload);
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
