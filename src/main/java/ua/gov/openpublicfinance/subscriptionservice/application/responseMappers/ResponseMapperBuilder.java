package ua.gov.openpublicfinance.subscriptionservice.application.responseMappers;

import ua.gov.openpublicfinance.subscriptionservice.application.SubscriptionThemes;

public class ResponseMapperBuilder {
    private SubscriptionThemes theme;

    public ResponseMapperBuilder setTheme(SubscriptionThemes theme) {
        this.theme = theme;
        return this;
    }

    public ResponseMapper build() {
        if ( notReady() ) {
            throw new IllegalArgumentException();
        }
        switch ( theme ) {
            case DOCUMENTS:{
                return new DocumentsResponseMapper();
            }
            case TRANSACTIONS:{
                return new TransactionsResponseMapper();
            }
            default:{
                return new StateResponseMapper();
            }
        }
    }

    private boolean notReady() {
        return ( theme == null );
    }
}
