package ua.gov.openpublicfinance.subscriptionservice;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    final private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Message<String> message) {
        String payload = (message.getPayload()).toString();
        System.out.println("==========================");
        System.out.println("Received <" + payload + ">");
        System.out.println("==========================");
        latch.countDown();
    }

    public void receiveMessage(Object message) {
        System.out.println("=======Receive Object======");
        System.out.println(message.toString());
        System.out.println("==========================");
    }
    public CountDownLatch getLatch() {
        return latch;
    }
}
