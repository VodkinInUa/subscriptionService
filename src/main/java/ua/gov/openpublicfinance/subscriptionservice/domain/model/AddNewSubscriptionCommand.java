package ua.gov.openpublicfinance.subscriptionservice.domain.model;

public class AddNewSubscriptionCommand {
    final private String target;
    final private String theme;
    final private String subscriber;
    final private long subscriptionId;

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

    public AddNewSubscriptionCommand(String target, String theme, String subscriber, long subscriptionId) {
        this.target = target;
        this.theme = theme;
        this.subscriber = subscriber;
        this.subscriptionId = subscriptionId;
    }
}
