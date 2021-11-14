package ua.gov.openpublicfinance.subscriptionservice.application;

public enum SubscriptionThemes {
    STATE (new Theme("states","/spending/disposer/states")),
    DOCUMENTS(new Theme("documents", "/spending/disposer/states")),
    TRANSACTIONS(new Theme("transactions",  "/spending/statistic/transactions/"));

    public final Theme data;
    SubscriptionThemes(Theme data) {
        this.data = data;
    }
}
