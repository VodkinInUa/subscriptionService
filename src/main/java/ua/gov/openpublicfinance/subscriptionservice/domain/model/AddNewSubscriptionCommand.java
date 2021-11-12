package ua.gov.openpublicfinance.subscriptionservice.domain.model;

public class AddNewSubscriptionCommand {
    private final String target;
    private final String theme;
    private final String subscriber;
    private final long subscriptionId;

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
