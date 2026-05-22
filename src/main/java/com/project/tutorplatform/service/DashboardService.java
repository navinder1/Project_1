package com.project.tutorplatform.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Payment;
import com.project.tutorplatform.enums.PaymentStatus;
import com.project.tutorplatform.repository.BookingRepository;
import com.project.tutorplatform.repository.PaymentRepository;
import com.project.tutorplatform.repository.StudentRepository;
import com.project.tutorplatform.repository.TutorRepository;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    public DashboardService(UserRepository userRepository,
                            TutorRepository tutorRepository,
                            StudentRepository studentRepository,
                            BookingRepository bookingRepository,
                            PaymentRepository paymentRepository) {

        this.userRepository = userRepository;
        this.tutorRepository = tutorRepository;
        this.studentRepository = studentRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
    }

    public Map<String, Object> getDashboardStats() {

        Map<String, Object> stats = new HashMap<>();

        stats.put("totalUsers", userRepository.count());
        stats.put("totalTutors", tutorRepository.count());
        stats.put("totalStudents", studentRepository.count());
        stats.put("totalBookings", bookingRepository.count());

        BigDecimal revenue = paymentRepository.findAll()
                .stream()
                .filter(payment ->
                        payment.getStatus() == PaymentStatus.SUCCESS)
                .map(Payment::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        stats.put("totalRevenue", revenue);

        return stats;
    }
}