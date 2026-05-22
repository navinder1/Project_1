package com.project.tutorplatform.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.repository.TutorRepository;

@Service
public class AIRecommendationService {

    private final TutorRepository tutorRepository;

    public AIRecommendationService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<Tutor> recommendTutors(Long subjectId,
                                       Double minRating,
                                       BigDecimal maxPrice) {

        List<Tutor> tutors =
                tutorRepository.findBySubjects_Id(subjectId);

        return tutors.stream()
                .filter(Tutor::getIsApproved)
                .filter(Tutor::getIsAvailable)
                .filter(t -> t.getRating() != null &&
                        t.getRating() >= minRating)
                .filter(t -> t.getHourlyRate() != null &&
                        t.getHourlyRate().compareTo(maxPrice) <= 0)
                .sorted(Comparator.comparing(Tutor::getRating).reversed())
                .collect(Collectors.toList());
    }
}