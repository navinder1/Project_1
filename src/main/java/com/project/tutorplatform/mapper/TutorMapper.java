package com.project.tutorplatform.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.project.tutorplatform.dto.response.TutorResponse;
import com.project.tutorplatform.entity.Tutor;

public class TutorMapper {

    public static TutorResponse toResponse(Tutor tutor) {

        if (tutor == null) return null;

        TutorResponse res = new TutorResponse();

        res.setId(tutor.getId());
        res.setUserId(tutor.getUser().getId());
        res.setFullName(tutor.getUser().getFullName());
        res.setEmail(tutor.getUser().getEmail());
        res.setExperience(tutor.getExperience());
        res.setHourlyRate(tutor.getHourlyRate());
        res.setRating(tutor.getRating());
        res.setTotalReviews(tutor.getTotalReviews());
        res.setIsAvailable(tutor.getIsAvailable());
        res.setIsApproved(tutor.getIsApproved());
        res.setSubscriptionPlan(tutor.getSubscriptionPlan());

        if (tutor.getSubjects() != null) {
            Set<String> subjects = tutor.getSubjects()
                    .stream()
                    .map(s -> s.getName())
                    .collect(Collectors.toSet());
            res.setSubjects(subjects);
        }

        return res;
    }
}