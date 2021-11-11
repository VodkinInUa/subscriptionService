package ua.gov.openpublicfinance.subscriptionservice.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ua.gov.openpublicfinance.subscriptionservice.domain.model.Subscription;

import java.lang.reflect.Array;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Subscription[] findByTheme(@Param("theme") String theme);
    Optional<Subscription> findOneBySubscriptionId(@Param("subscriptionId") long subscriptionId);
}
