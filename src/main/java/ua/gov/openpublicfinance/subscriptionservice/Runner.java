package ua.gov.openpublicfinance.subscriptionservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    public Runner(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0; i<10; i++){
            Random random = new Random();
            String payload = this.subscribeInJson(edrpous[i],themes[random.nextInt(themes.length)]);
            Message<String> eventMessage = MessageBuilder
                    .withPayload(payload)
                    .setHeader("Subscriber", "1234567890")
                    .build();
            rabbitTemplate.convertAndSend(SubscriptionServiceApplication.directExchangeName, "tg.subscribe", eventMessage);
        }

    }
    private String[] themes = {
            "states",
            "documents",
            "transactions"
    };


    private String[] edrpous = {
            "00034022",
            "00032684",
            "00032684",
            "37508596",
            "37552996",
            "00026620",
            "43672853",
            "40446210",
            "37472062",
            "38649881",
            "00034022",
            "38621185",
            "00012925",
            "37471928",
            "37567866",
            "42657144",
            "00013480",
            "43220851",
            "00015622",
            "43733545"};
    private String subscribeInJson(String edropu, String theme) {
        String json = "{\"target\": \""+edropu+"\"," +
                "\"theme\": \""+theme+"\"," +
                "\"subscriber\": \"111111111\"," +
                "\"subscriptionId\": \""+randInt(100000,999999)+"\"" +
                "}";
        return json;
    }

    private int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        //
        // In particular, do NOT do 'Random rand = new Random()' here or you
        // will get not very good / not very random results.
        Random random = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
