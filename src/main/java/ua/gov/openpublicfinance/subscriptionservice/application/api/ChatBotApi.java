package ua.gov.openpublicfinance.subscriptionservice.application.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ChatBotApi {
    private final Logger logger = LoggerFactory.getLogger(ChatBotApi.class);
    private final String BASE_URL = "http://chat-bot.openbudget.gov.ua";

    public String consumeApi(ApiRequest apiRequest,String uri){
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
