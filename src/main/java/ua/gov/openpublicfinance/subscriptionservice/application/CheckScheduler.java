package ua.gov.openpublicfinance.subscriptionservice.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories.SubscriptionRepository;


@Service
@EnableScheduling
public class CheckScheduler {
    private final Logger logger = LoggerFactory.getLogger(CheckScheduler.class);
    private final SubscriptionRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final String BASE_URL = "http://chat-bot.openbudget.gov.ua";

    public CheckScheduler(SubscriptionRepository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = "1/30 * * * * ?",zone="Europe/Kiev")
    public void checkStates(){
        logger.info("Starting check for theme \"state\" …");
        String uri = "/spending/disposer/states";
        SubscriptionsCheck subscriptions = new SubscriptionsCheck(repository, applicationEventPublisher, StateResponseMapper.class);
        ApiRequest requestBody = subscriptions.getRequestForCheck();
        String response = this.consumeApi(requestBody,uri);
        subscriptions.processResponse(response);
    }

    @Scheduled(cron = "1/30 * * * * ?",zone="Europe/Kiev")
    public void checkDocuments(){
        logger.info("Starting check for theme \"documents\" …");
        String uri = "/spending/statistic/documents/";
        SubscriptionsCheck subscriptions = new SubscriptionsCheck(repository, applicationEventPublisher,DocumentsResponseMapper.class);
        ApiRequest requestBody = subscriptions.getRequestForCheck();
        String response = this.consumeApi(requestBody,uri);
        subscriptions.processResponse(response);
    }

    @Scheduled(cron = "1/30 * * * * ?",zone="Europe/Kiev")
    public void checkTransactions(){
        logger.info("Starting check for theme \"transactions\" …");
        String uri = "/spending/statistic/transactions/";
        SubscriptionsCheck subscriptions = new SubscriptionsCheck(repository, applicationEventPublisher,TransactionsResponseMapper.class);
        ApiRequest requestBody = subscriptions.getRequestForCheck();
        String response = this.consumeApi(requestBody,uri);
        subscriptions.processResponse(response);
    }

    private String consumeApi(ApiRequest apiRequest,String uri){
        Mono<ApiRequest> requestBody = Mono.just(apiRequest);
        WebClient client = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        String infoResponse = "{}";
        try {
            infoResponse = client
                .post()
                .uri(uri)
                .body(requestBody, ApiRequest.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(String.class) // error body as String or other class
                        .flatMap(error -> Mono.error(new RuntimeException(error)))) // throw a functional exception
                .bodyToMono(String.class)
                .block();
        } catch (RuntimeException e) {
            this.handleError(e);
        }

        logger.info("Response from "+uri+" \""+infoResponse+"\" ");
        return infoResponse;
    }

    private void handleError(Throwable error){
        logger.error("Error consuming api",error);
        //TODO process exception api consume
    }
}
