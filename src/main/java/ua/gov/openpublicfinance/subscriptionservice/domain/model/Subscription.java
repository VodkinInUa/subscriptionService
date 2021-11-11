package ua.gov.openpublicfinance.subscriptionservice.domain.model;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "target")
    private String target;

    @Column(name = "theme")
    private String theme;

    @Column(name = "subscriber")
    private String subscriber;

    @Column(name = "last_check_datatime")
    private long lastCheckDataTime;

    @Column(name = "last_check_result")
    private String lastCheckResult;

    @Column(name = "subscription_id")
    private long subscriptionId;

    public Subscription(){};

    public Subscription(String target, String theme, String subscriber, long subscriptionId) {
        this.target = target;
        this.theme = theme;
        this.subscriber = subscriber;
        this.subscriptionId = subscriptionId;
    }

    public long getId() {
        return id;
    }
    public String getTarget() {
        return target;
    }
    public String getLastCheckResult(){
        return lastCheckResult;
    }

    public String getTheme() {
        return theme;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setLastCheckResult(String result){
        this.lastCheckResult = result;
        this.lastCheckDataTime = Instant.now().getEpochSecond();
    }

    public long getSubscriptionId() {
        return subscriptionId;
    }
}
