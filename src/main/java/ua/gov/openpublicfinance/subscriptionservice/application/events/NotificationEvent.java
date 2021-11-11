package ua.gov.openpublicfinance.subscriptionservice.application.events;

import org.springframework.context.ApplicationEvent;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;

public class NotificationEvent extends ApplicationEvent {
    final private Subscription subscription;
    final private String payload;
    public NotificationEvent(Object source, Subscription subscription, String payload) {
        super(source);
        this.subscription = subscription;
        this.payload = payload;
    }
    public String getPayload() {
        return payload;
    }
    public String getSubscriber() {
        return this.subscription.getSubscriber();
    }
}
