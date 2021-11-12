package ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq.messages;

public class SubscribeMessage {
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

    public SubscribeMessage setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public SubscribeMessage setTarget(String target) {
        this.target = target;
        return this;
    }

    public SubscribeMessage setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public SubscribeMessage setSubscriber(String subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    @Override
    public String toString() {
        return "SubscribeCommand{" +
                "target='" + target + '\'' +
                ", theme='" + theme + '\'' +
                ", subscriber='" + subscriber + '\'' +
                ", subscriptionId=" + subscriptionId +
                '}';
    }
}
