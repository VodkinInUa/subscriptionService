package ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ua.gov.openpublicfinance.subscriptionservice.application.events.NotificationEvent;

import java.time.Instant;

@Component
public class RabbitMqPublisher implements ApplicationListener<NotificationEvent> {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMqPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onApplicationEvent(NotificationEvent event) {
        Message<String> message = this.messageFromEvent(event);
        rabbitTemplate.convertAndSend("tg.bot.notification","tg.notification",message);
    }

    private Message<String> messageFromEvent(NotificationEvent event){
        String payload = event.getPayload();
        Message<String> eventMessage = MessageBuilder
                .withPayload(payload)
                .setHeader("Send datetime", Instant.now().getEpochSecond())
                .setHeader("Subscriber", event.getSubscriber())
                .build();
        return  eventMessage;
    }
}
