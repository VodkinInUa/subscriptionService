package ua.gov.openpublicfinance.subscriptionservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Iterator;


@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) {

        TestPayload testPayload = new TestPayload();
        Iterator<String> iterator = testPayload.iterator();
        while ( iterator.hasNext() ) {
            String payload = iterator.next();
            Message<String> eventMessage = MessageBuilder
                    .withPayload(payload)
                    .setHeader("Subscriber", "1234567890")
                    .build();
            rabbitTemplate.convertAndSend(SubscriptionServiceApplication.directExchangeName, "tg.subscribe", eventMessage);
        }
    }

}
