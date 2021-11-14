package ua.gov.openpublicfinance.subscriptionservice.application;

public class Theme {
    private final String title;
    private final String uri;

    public Theme(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }
}
