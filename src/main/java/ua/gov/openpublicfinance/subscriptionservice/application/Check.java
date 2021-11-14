package ua.gov.openpublicfinance.subscriptionservice.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.gov.openpublicfinance.subscriptionservice.application.api.ApiRequest;
import ua.gov.openpublicfinance.subscriptionservice.application.api.ChatBotApi;
import ua.gov.openpublicfinance.subscriptionservice.application.events.NotificationEvent;
import ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules.NotificationRule;
import ua.gov.openpublicfinance.subscriptionservice.application.notifiationRules.NotificationRuleBuilder;
import ua.gov.openpublicfinance.subscriptionservice.application.responseMappers.ResponseMapper;
import ua.gov.openpublicfinance.subscriptionservice.application.responseMappers.ResponseMapperBuilder;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories.SubscriptionRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Check {
    private final Logger logger = LoggerFactory.getLogger(Check.class);
    final private SubscriptionRepository repository;
    final private ApplicationEventPublisher applicationEventPublisher;

    final private Subscription[] subscriptions;
    final private MultiValueMap<String,Subscription> subscriptionsMap = new LinkedMultiValueMap<>();
    private ApiRequest requestForCheck;
    private final SubscriptionThemes theme;
    private ChatBotApi api;


    Check(SubscriptionRepository repository, ApplicationEventPublisher applicationEventPublisher, SubscriptionThemes theme){
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.theme = theme;
        this.subscriptions = this.getAllSuscriptiosForTheme(theme.data.getTitle());
        this.init();
    }

    private void init() {
        api = new ChatBotApi();
        String[] edrpous = Arrays.stream(subscriptions).map(c -> c.getTarget()).toArray(String[]::new);
        requestForCheck = new ApiRequest(edrpous);
        Arrays.stream(subscriptions).forEach(c -> subscriptionsMap.add(c.getTarget(),c));
    }

    private Subscription[] getAllSuscriptiosForTheme(String themeTitle){
        return repository.findByTheme(themeTitle);
    }

    public ApiRequest getRequestForCheck() {
        return requestForCheck;
    }

    public MultiValueMap<String, Subscription> getSubscriptionsMap() {
        return subscriptionsMap;
    }

    public void process(){
        ApiRequest requestBody = getRequestForCheck();
        String response = api.consumeApi(requestBody,theme.data.getUri());
        processResponse(response);
    }


    private void processResponse(String response){
        HashMap<String,String> responseMap = this.mapFromJson(response);
        for (Map.Entry<String, ?> entry : responseMap.entrySet()) {
            String edrpou = entry.getKey();
            String result = String.valueOf( entry.getValue() );
            List<Subscription> subscriptionsForEdrpou = subscriptionsMap.get(edrpou);
            for (Subscription subscription: subscriptionsForEdrpou) {
                String lastCheckResult = subscription.getLastCheckResult();
                subscription.setLastCheckResult(result);
                repository.save(subscription);
                this.notifyIfNecessary(lastCheckResult,result, subscription);
            }
        }
    }

    private void notifyIfNecessary(String lastCheckResult, String result, Subscription subscription) {
        NotificationRuleBuilder builder = new NotificationRuleBuilder();
        NotificationRule rule = builder
                .setTheme(theme.data.getTitle())
                .setSubscription(subscription)
                .setResult(result)
                .setLastCheckResult(lastCheckResult)
                .build();
        if ( rule.notificationNecessary() ){
            logger.info("Notification necessary for target \""+subscription.getTarget()+"\", theme \""+subscription.getTheme()+"\".");
            NotificationEvent notification = new NotificationEvent(this, subscription, result);
            applicationEventPublisher.publishEvent(notification);
        }
    }

    private HashMap<String,String> mapFromJson (String json){
        ResponseMapper mapper = new ResponseMapperBuilder()
                .setTheme(theme)
                .build();
        return mapper.mapFromJson(json);
    }
}
