package ua.gov.openpublicfinance.subscriptionservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SubscriptionServiceApplication {

    static final String directExchangeName = "tg.bot.exchange";
    static final String queueSubscribeName = "subscribe";
    static final String queueUnsubscribeName = "unsubscribe";


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(directExchangeName);
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

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//                                             MessageListenerAdapter listenerAdapter) {
//
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueInfoName,queueDocumentsName,queueTransactionsName,queueStatesName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(Receiver receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
    public static void main(String[] args) {
        SpringApplication.run(SubscriptionServiceApplication.class, args);
    }

}
