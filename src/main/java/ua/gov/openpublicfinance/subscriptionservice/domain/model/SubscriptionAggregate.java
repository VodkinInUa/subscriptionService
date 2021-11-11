package ua.gov.openpublicfinance.subscriptionservice.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories.SubscriptionRepository;

import java.util.List;

@Component
public class SubscriptionAggregate {
    private final Logger logger = LoggerFactory.getLogger(SubscriptionAggregate.class);
    @Autowired
    private SubscriptionRepository repository;

    public Long addNewSubscription(AddNewSubscriptionCommand command) {
        Subscription subscription = new Subscription(command.getTarget(),command.getTheme(),command.getSubscriber(),command.getSubscriptionId());
        repository.save(subscription);
        logger.info("Save new subscription id="+subscription.getId()+" command=\""+command+"\"");
        return subscription.getId();
    }

    public void removeSubscription(UnsubscribeCommand command){
        long subscriptionId = command.getSubscriptionId();
        try {
            Subscription subscriptionForDelete = repository.findOneBySubscriptionId(subscriptionId).orElseThrow(RuntimeException::new);
            repository.delete(subscriptionForDelete);
            logger.info("Remove subscription id="+subscriptionForDelete.getId()+" command=\""+command+"\"");
        } catch (RuntimeException e) {
            logger.error("Can`t find subscription with \"subscriptionId = "+command.getSubscriptionId()+" \".");
        }

    }
}
