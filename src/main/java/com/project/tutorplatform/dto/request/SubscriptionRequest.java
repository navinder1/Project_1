package com.project.tutorplatform.dto.request;

import com.project.tutorplatform.enums.SubscriptionPlan;

public class SubscriptionRequest {

    private Long tutorId;
    private SubscriptionPlan plan;

    public Long getTutorId() {
        return tutorId;
    }

    public void setTutorId(Long tutorId) {
        this.tutorId = tutorId;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlan plan) {
        this.plan = plan;
    }
}