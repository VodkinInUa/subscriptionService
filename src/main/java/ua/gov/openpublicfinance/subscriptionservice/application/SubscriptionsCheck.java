package ua.gov.openpublicfinance.subscriptionservice.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.gov.openpublicfinance.subscriptionservice.application.events.NotificationEvent;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories.SubscriptionRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionsCheck {
    private final Logger logger = LoggerFactory.getLogger(SubscriptionsCheck.class);
    final private SubscriptionRepository repository;
    final private ApplicationEventPublisher applicationEventPublisher;

    final private Subscription[] subscriptions;
    final private MultiValueMap<String,Subscription> subscriptionsMap = new LinkedMultiValueMap<>();
    private ApiRequest requestForCheck;
    private ResponseMapper mapper;


    SubscriptionsCheck(SubscriptionRepository repository, ApplicationEventPublisher applicationEventPublisher, Class mapperClass){
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
        try {
            this.mapper = (ResponseMapper) mapperClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Can`t instantiate class \""+mapperClass.getName()+"\"");
        }
        this.subscriptions = this.getAllSuscriptiosForTheme(mapper.getTheme());
        this.init();
    }

    private void init() {
        String[] edrpous = Arrays.stream(subscriptions).map(c -> c.getTarget()).toArray(String[]::new);
        requestForCheck = new ApiRequest(edrpous);
        Arrays.stream(subscriptions).forEach(c -> subscriptionsMap.add(c.getTarget(),c));
    }

    private Subscription[] getAllSuscriptiosForTheme(String theme){
        return repository.findByTheme(theme);
    }

    public ApiRequest getRequestForCheck() {
        return requestForCheck;
    }

    public MultiValueMap<String, Subscription> getSubscriptionsMap() {
        return subscriptionsMap;
    }

    public void processResponse(String response){
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
        NotificationRules rule = new NotificationRules(lastCheckResult,result,subscription);
        if ( rule.notificationNecessary() ){
            logger.info("Notification necessary");
            NotificationEvent notification = new NotificationEvent(this, subscription, result);
            applicationEventPublisher.publishEvent(notification);
        }
    }

    private HashMap<String,String> mapFromJson (String json){
        return mapper.mapFromJson(json);
    }
}
