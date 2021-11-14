package ua.gov.openpublicfinance.subscriptionservice.application.api;

import java.util.Arrays;

public class ApiRequest {
    private String[] edrpous;

    public ApiRequest(String ... edrpous) {
        this.edrpous = edrpous;
    }

    public String[] getEdrpous() {
        return edrpous;
    }

    public ApiRequest setEdrpous(String[] edrpous) {
        this.edrpous = edrpous;
        return this;
    }

    @Override
    public String toString() {
        return "infoRequest{" +
                "edrpous=" + Arrays.toString(edrpous) +
                '}';
    }
}
