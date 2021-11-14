package ua.gov.openpublicfinance.subscriptionservice.application;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.gov.openpublicfinance.subscriptionservice.application.events.NewSubscriptionReceivedEvent;
import ua.gov.openpublicfinance.subscriptionservice.application.events.UnsubscribeEvent;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.AddNewSubscriptionCommand;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.SubscriptionAggregate;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.UnsubscribeCommand;

@Component
public class EventService {

    private final SubscriptionAggregate aggregate;

    public EventService(SubscriptionAggregate aggregate) {
        this.aggregate = aggregate;
    }

    @EventListener
    public void onApplicationEvent(NewSubscriptionReceivedEvent event) {
        AddNewSubscriptionCommand command = new AddNewSubscriptionCommand(event.getTarget(),event.getTheme(),event.getSubscriber(),event.getSubscriptionId());
        aggregate.addNewSubscription(command);
    }

    @EventListener
    public void onApplicationEvent(UnsubscribeEvent event) {
        UnsubscribeCommand command = new UnsubscribeCommand(event.getTarget(),event.getTheme(),event.getSubscriber(),event.getSubscriptionId());
        aggregate.removeSubscription(command);
    }


}
