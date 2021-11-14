package ua.gov.openpublicfinance.subscriptionservice.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories.SubscriptionRepository;


@Service
@EnableScheduling
public class CheckScheduler {
    private final Logger logger = LoggerFactory.getLogger(CheckScheduler.class);
    private final SubscriptionRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CheckScheduler(SubscriptionRepository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = "1/30 * * * * ?",zone="Europe/Kiev")
    public void checkStates(){
        SubscriptionThemes theme = SubscriptionThemes.STATE;
        String themeTitle = theme.data.getTitle();
        logger.info("Starting check for theme \""+themeTitle+"\" …");
        Check subscriptions = new Check(repository, applicationEventPublisher, theme);
        subscriptions.process();
    }

    @Scheduled(cron = "1/30 * * * * ?",zone="Europe/Kiev")
    public void checkDocuments(){
        SubscriptionThemes theme = SubscriptionThemes.DOCUMENTS;
        String themeTitle = theme.data.getTitle();
        logger.info("Starting check for theme \""+themeTitle+"\" …");
        Check subscriptions = new Check(repository, applicationEventPublisher,theme);
        subscriptions.process();
    }

    @Scheduled(cron = "1/30 * * * * ?",zone="Europe/Kiev")
    public void checkTransactions(){
        SubscriptionThemes theme = SubscriptionThemes.TRANSACTIONS;
        String themeTitle = theme.data.getTitle();
        logger.info("Starting check for theme \""+themeTitle+"\" …");
        Check subscriptions = new Check(repository, applicationEventPublisher,theme);
        subscriptions.process();
    }
}
