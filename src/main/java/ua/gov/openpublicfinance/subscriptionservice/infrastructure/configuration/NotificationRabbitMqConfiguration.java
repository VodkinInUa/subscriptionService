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
    static final public String notificationDirectExchangeName = "tg.bot.notification";
    static final String queueNotificationName = "notification";
    static final public String subscriptionDirectExchangeName = "tg.bot.exchange";
    static final String queueSubscribeName = "subscribe";
    static final String queueUnsubscribeName = "unsubscribe";


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(subscriptionDirectExchangeName);
    }

    @Bean
    Queue queueSubscribe() {
        return new Queue(queueSubscribeName, false);
    }
    @Bean
    Queue queueUnsubscribe() {
        return new Queue(queueUnsubscribeName, false);
    }

    @Bean
    Binding bindingSubscribe(Queue queueSubscribe, DirectExchange exchange) {
        return BindingBuilder.bind(queueSubscribe).to(exchange).with("tg.subscribe");
    }
    @Bean
    Binding bindingUnsubscribe(Queue queueUnsubscribe, DirectExchange exchange) {
        return BindingBuilder.bind(queueUnsubscribe).to(exchange).with("tg.unsubscribe");
    }


    @Bean
    DirectExchange exchangeNotification() {
        return new DirectExchange(notificationDirectExchangeName);
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
