package ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules;

import ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules.NotificationRule;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;

public class TransactionsNotificationRule implements NotificationRule {
    final private String lastCheckResult;
    final private String result;
    final private Subscription subscription;

    public TransactionsNotificationRule(String lastCheckResult, String result, Subscription subscription) {
        this.lastCheckResult = lastCheckResult;
        this.result = result;
        this.subscription = subscription;
    }

    public boolean notificationNecessary() {
        return true;
    }
}
