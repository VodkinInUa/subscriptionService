package ua.gov.openpublicfinance.subscriptionservice.domain.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories.SubscriptionRepository;

import java.util.List;

@Component
public class SubscriptionAggregate {

    @Autowired
    private SubscriptionRepository repository;

    public Long addNewSubscription(AddNewSubscriptionCommand command) {
        Subscription subscription = new Subscription(command.getTarget(),command.getTheme(),command.getSubscriber(),command.getSubscriptionId());
        repository.save(subscription);
        return subscription.getId();
    }

    public void removeSubscription(UnsubscribeCommand command){
        long subscriptionId = command.getSubscriptionId();
        try {
            Subscription subscriptionForDelete = repository.findOneBySubscriptionId(subscriptionId).orElseThrow(RuntimeException::new);
            repository.delete(subscriptionForDelete);
        } catch (RuntimeException e) {
            String error = "Can`t find subscription with \"subscriptionId = "+command.getSubscriptionId()+" \".";
            System.err.println(error);
        }

    }
}
