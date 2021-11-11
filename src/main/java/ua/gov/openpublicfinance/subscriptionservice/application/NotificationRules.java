package ua.gov.openpublicfinance.subscriptionservice.application;

import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;

public class NotificationRules {
    final private String lastCheckResult;
    final private String result;
    final private Subscription subscription;

    public NotificationRules(String lastCheckResult, String result, Subscription subscription) {
        this.lastCheckResult = lastCheckResult;
        this.result = result;
        this.subscription = subscription;
    }

    public boolean notificationNecessary() {
        switch (subscription.getTheme()) {
            case "state":
                return stateRule();
            case "documents":
                return documentsRule();
            case "transactions":
                return transactionsRule();
        }
        return false;
    }

    private boolean transactionsRule() {
        return true;
    }
    private boolean stateRule() {
        return lastCheckResult != null && ! lastCheckResult.equals(result);
    }
    private boolean documentsRule() {
        return true;
    }
}
