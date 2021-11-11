package ua.gov.openpublicfinance.subscriptionservice.infrastructure.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class NotificationRabbitMqConfiguration {
    static final String directExchangeName = "tg.bot.notification";
    static final String queueNotificationName = "notification";


    @Bean
    DirectExchange exchangeNotification() {
        return new DirectExchange(directExchangeName);
    }

    @Bean
    Queue queueNotification() {
        return new Queue(queueNotificationName, false);
    }

    @Bean
    Binding bindingNotification(Queue queueNotification, DirectExchange exchangeNotification) {
        return BindingBuilder.bind(queueNotification).to(exchangeNotification).with("tg.notification");
    }
}
