package ua.gov.openpublicfinance.subscriptionservice.application.events;

import org.springframework.context.ApplicationEvent;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq.messages.SubscribeMessage;

public class NewSubscriptionReceivedEvent extends ApplicationEvent{
    private String target;
    private String theme;
    private String subscriber;
    private long subscriptionId;

    public String getTarget() {
        return target;
    }

    public String getTheme() {
        return theme;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }

    public NewSubscriptionReceivedEvent setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public NewSubscriptionReceivedEvent setTarget(String target) {
        this.target = target;
        return this;
    }

    public NewSubscriptionReceivedEvent setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public NewSubscriptionReceivedEvent setSubscriber(String subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    public NewSubscriptionReceivedEvent(Object source, String target, String theme, String subscriber, long subscriptionId) {
        super(source);
        this.target = target;
        this.theme = theme;
        this.subscriber = subscriber;
        this.subscriptionId = subscriptionId;
    }

    public NewSubscriptionReceivedEvent(Object source, SubscribeMessage command) {
        super(source);
        this.target = command.getTarget();
        this.theme = command.getTheme();
        this.subscriber = command.getSubscriber();
        this.subscriptionId = command.getSubscriptionId();
    }

}
