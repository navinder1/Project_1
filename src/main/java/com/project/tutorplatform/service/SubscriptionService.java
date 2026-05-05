package com.project.tutorplatform.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Subscription;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.enums.SubscriptionPlan;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.SubscriptionRepository;
import com.project.tutorplatform.repository.TutorRepository;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final TutorRepository tutorRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               TutorRepository tutorRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.tutorRepository = tutorRepository;
    }

    public Subscription createSubscription(Long tutorId,
                                           SubscriptionPlan plan,
                                           double amount,
                                           String razorpaySubId) {

        Tutor tutor = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found"));

        // deactivate old subscriptions
        subscriptionRepository.deactivateAllByTutorId(tutorId);

        Subscription subscription = new Subscription();
        subscription.setTutor(tutor);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDate.now());
        subscription.setActive(true);
        subscription.setAmount(amount);
        subscription.setRazorpaySubscriptionId(razorpaySubId);

        // 🔥 Plan duration logic
        switch (plan) {
            case BASIC -> subscription.setEndDate(LocalDate.now().plusDays(30));
            case STANDARD -> subscription.setEndDate(LocalDate.now().plusDays(90));
            case PREMIUM -> subscription.setEndDate(LocalDate.now().plusDays(180));
        }

        return subscriptionRepository.save(subscription);
    }

    public Tutor getActiveSubscription(Long tutorId) {
        return subscriptionRepository.findByTutorIdAndIsActiveTrue(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("No active subscription"));
    }

    public boolean isActive(Long tutorId) {
        return subscriptionRepository.findByTutorIdAndIsActiveTrue(tutorId).isPresent();
    }

    public void expireSubscriptions() {

        LocalDate today = LocalDate.now();

        subscriptionRepository.findAll().forEach(sub -> {
            if (sub.isActive() && sub.getEndDate().isBefore(today)) {
                sub.setActive(false);
                subscriptionRepository.save(sub);
            }
        });
    }
}