package ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules;

public interface NotificationRule {
    default boolean notificationNecessary() {
        return false;
    }
}
