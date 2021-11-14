package ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules;

import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;

public class StateNotificationRule implements NotificationRule{
    final private String lastCheckResult;
    final private String result;
    final private Subscription subscription;

    public StateNotificationRule(String lastCheckResult, String result, Subscription subscription) {
        this.lastCheckResult = lastCheckResult;
        this.result = result;
        this.subscription = subscription;
    }

    public boolean notificationNecessary() {
        return ( lastCheckResult != null && ! result.equals(lastCheckResult) ) ;
    }
}
