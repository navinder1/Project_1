package com.project.tutorplatform.dto.response;

import java.math.BigDecimal;
import java.util.Set;

import com.project.tutorplatform.enums.SubscriptionPlan;
import com.project.tutorplatform.enums.TeachingMode;

public class TutorResponse {

    private Long id;
    private Long userId;
    private String fullName;
    private String bio;
    private String qualifications;
    private Integer experience;
    private BigDecimal hourlyRate;
    private Set<Long> subjectIds;
    private TeachingMode teachingMode;
    private Double rating;
    private Integer totalReviews;
    private Boolean isAvailable;
    private Boolean isApproved;
    private SubscriptionPlan subscriptionPlan;
    private BigDecimal walletBalance;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public Set<Long> getSubjectIds() { return subjectIds; }
    public void setSubjectIds(Set<Long> subjectIds) { this.subjectIds = subjectIds; }

    public TeachingMode getTeachingMode() { return teachingMode; }
    public void setTeachingMode(TeachingMode teachingMode) { this.teachingMode = teachingMode; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public Boolean getIsApproved() { return isApproved; }
    public void setIsApproved(Boolean isApproved) { this.isApproved = isApproved; }

    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) { this.subscriptionPlan = subscriptionPlan; }

    public BigDecimal getWalletBalance() { return walletBalance; }
    public void setWalletBalance(BigDecimal walletBalance) { this.walletBalance = walletBalance; }
}