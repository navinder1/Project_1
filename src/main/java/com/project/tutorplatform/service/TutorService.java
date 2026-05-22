package com.project.tutorplatform.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Location;
import com.project.tutorplatform.entity.Subject;
import com.project.tutorplatform.entity.Tutor;
import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.SubjectRepository;
import com.project.tutorplatform.repository.TutorRepository;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public TutorService(TutorRepository tutorRepository,
                        UserRepository userRepository,
                        SubjectRepository subjectRepository) {
        this.tutorRepository = tutorRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public Tutor createTutor(Long userId, Tutor tutor) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        tutor.setUser(user);
        tutor.setIsApproved(false);
        tutor.setIsAvailable(true);
        tutor.setRating(0.0);
        tutor.setTotalReviews(0);

        return tutorRepository.save(tutor);
    }

    public Tutor getById(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found"));
    }

    public Tutor updateTutor(Long id, Tutor updated) {

        Tutor tutor = getById(id);

        tutor.setBio(updated.getBio());
        tutor.setQualifications(updated.getQualifications());
        tutor.setExperience(updated.getExperience());
        tutor.setHourlyRate(updated.getHourlyRate());
        tutor.setTeachingMode(updated.getTeachingMode());

        return tutorRepository.save(tutor);
    }

    public Tutor updateLocation(Long tutorId, Location location) {

        Tutor tutor = getById(tutorId);
        tutor.setLocation(location);

        return tutorRepository.save(tutor);
    }

    public Tutor updateSubjects(Long tutorId, List<Long> subjectIds) {

        Tutor tutor = getById(tutorId);

        List<Subject> subjects = subjectRepository.findAllById(subjectIds);

        tutor.setSubjects(Set.copyOf(subjects));

        return tutorRepository.save(tutor);
    }

    public Page<Tutor> getApprovedTutors(Pageable pageable) {
        return tutorRepository.findByIsApprovedAndIsAvailable(true, true, pageable);
    }

    public List<Tutor> getBySubject(Long subjectId) {
        return tutorRepository.findBySubjects_Id(subjectId);
    }

    public List<Tutor> getByRating(Double rating) {
        return tutorRepository.findByRatingGreaterThanEqual(rating);
    }

    public List<Tutor> getByPriceRange(BigDecimal min, BigDecimal max) {
        return tutorRepository.findByHourlyRateBetween(min, max);
    }

    public void approveTutor(Long tutorId) {

        Tutor tutor = getById(tutorId);
        tutor.setIsApproved(true);

        tutorRepository.save(tutor);
    }

    public void toggleAvailability(Long tutorId, boolean available) {

        Tutor tutor = getById(tutorId);
        tutor.setIsAvailable(available);

        tutorRepository.save(tutor);
    }

    public void updateRating(Long tutorId, double newRating) {

        Tutor tutor = getById(tutorId);

        int totalReviews = tutor.getTotalReviews() != null ? tutor.getTotalReviews() : 0;
        double currentRating = tutor.getRating() != null ? tutor.getRating() : 0.0;

        double updatedRating =
                ((currentRating * totalReviews) + newRating) / (totalReviews + 1);

        tutor.setRating(updatedRating);
        tutor.setTotalReviews(totalReviews + 1);

        tutorRepository.save(tutor);
    }

    public void addToWallet(Long tutorId, BigDecimal amount) {

        Tutor tutor = getById(tutorId);

        BigDecimal current = tutor.getWalletBalance() != null
                ? tutor.getWalletBalance()
                : BigDecimal.ZERO;

        tutor.setWalletBalance(current.add(amount));

        tutorRepository.save(tutor);
    }

	public @Nullable Object getAllTutors() {
		return null;
	}

	public @Nullable Object getTutorById(Long id) {
		return null;
	}

	public @Nullable Object getTutorsBySubject(Long subjectId) {
		return null;
	}
}