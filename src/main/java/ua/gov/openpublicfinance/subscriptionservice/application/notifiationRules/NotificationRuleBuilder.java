package ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules;

import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;

public class NotificationRuleBuilder {
    private String theme;
    private String lastCheckResult;
    private String result;
    private Subscription subscription;

    public NotificationRuleBuilder() {
    }
    public NotificationRuleBuilder(String theme, String lastCheckResult, String result, Subscription subscription) {
        this.theme = theme;
        this.lastCheckResult = lastCheckResult;
        this.result = result;
        this.subscription = subscription;
    }

    public String getTheme() {
        return theme;
    }

    public NotificationRuleBuilder setTheme(String theme) {
        this.theme = theme;
        return this;
    }

    public String getLastCheckResult() {
        return lastCheckResult;
    }

    public NotificationRuleBuilder setLastCheckResult(String lastCheckResult) {
        this.lastCheckResult = lastCheckResult;
        return this;
    }

    public String getResult() {
        return result;
    }

    public NotificationRuleBuilder setResult(String result) {
        this.result = result;
        return this;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public NotificationRuleBuilder setSubscription(Subscription subscription) {
        this.subscription = subscription;
        return this;
    }

    public NotificationRule build() {
        if ( notReady() ) {
            throw new IllegalArgumentException();
        }
        NotificationRule rule;
        switch ( theme ){
            case ( "state" ): {
                rule =  new StateNotificationRule(lastCheckResult,result,subscription);
                break;
            }
            case ( "documents" ): {
                rule =  new DocumentsNotificationRule(lastCheckResult,result,subscription);
                break;
            }
            case ( "transactions" ): {
                rule =  new TransactionsNotificationRule(lastCheckResult,result,subscription);
                break;
            }
            default: {
                rule = new StateNotificationRule(lastCheckResult,result,subscription);
            }
        }
        return rule;
    }


    private boolean notReady() {
        return ( theme == null && lastCheckResult == null && result == null && subscription == null );
    }
}
