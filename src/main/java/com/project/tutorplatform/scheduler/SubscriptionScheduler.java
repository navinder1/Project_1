package com.project.tutorplatform.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.project.tutorplatform.entity.Subscription;
import com.project.tutorplatform.repository.SubscriptionRepository;

@Component
public class SubscriptionScheduler {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionScheduler(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void expireSubscriptions() {

        List<Subscription> subscriptions =
                subscriptionRepository.findByIsActiveTrue();

        for (Subscription s : subscriptions) {
            if (s.getEndDate().isBefore(LocalDate.now())) {
                s.setActive(false);
                subscriptionRepository.save(s);
            }
        }
    }
}