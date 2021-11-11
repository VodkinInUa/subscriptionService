package ua.gov.openpublicfinance.subscriptionservice.infrastructure.services.rabbitMq.messages;

public class UnsubscribeMessage {
    private String target;
    private String theme;
    private String subscriber;
    private long subscriptionId;

//    public SubscribeCommand(String target, String theme, String subscriber) {
//        this.target = target;
//        this.theme = theme;
//        this.subscriber = subscriber;
//    }


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

    public UnsubscribeMessage setSubscriptionId(long subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    public UnsubscribeMessage setTarget(String target) {
        this.target = target;
        return this;
    }

    public UnsubscribeMessage setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public UnsubscribeMessage setSubscriber(String subscriber) {
        this.subscriber = subscriber;
        return this;
    }

    @Override
    public String toString() {
        return "UnsubscribeCommand{" +
                "target='" + target + '\'' +
                ", theme='" + theme + '\'' +
                ", subscriber='" + subscriber + '\'' +
                ", subscriptionId=" + subscriptionId +
                '}';
    }
}
