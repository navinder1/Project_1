package com.project.tutorplatform.service;

import java.util.List;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.project.tutorplatform.entity.Location;
import com.project.tutorplatform.entity.Student;
import com.project.tutorplatform.entity.Subject;
import com.project.tutorplatform.entity.User;
import com.project.tutorplatform.exception.ResourceNotFoundException;
import com.project.tutorplatform.repository.StudentRepository;
import com.project.tutorplatform.repository.SubjectRepository;
import com.project.tutorplatform.repository.UserRepository;

@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final UserRepository userRepository;
	private final SubjectRepository subjectRepository;

	public StudentService(StudentRepository studentRepository, UserRepository userRepository,
			SubjectRepository subjectRepository) {
		this.studentRepository = studentRepository;
		this.userRepository = userRepository;
		this.subjectRepository = subjectRepository;
	}

	public Student createStudent(Long userId, Student student) {

		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

		student.setUser(user);
		student.setTotalBookings(0);

		return studentRepository.save(student);
	}

	public Student getById(Long id) {
		return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
	}

	public Student updateStudent(Long id, Student updated) {

		Student student = getById(id);

		student.setGradeLevel(updated.getGradeLevel());
		student.setPreferredMode(updated.getPreferredMode());
		student.setParentName(updated.getParentName());
		student.setParentPhone(updated.getParentPhone());

		return studentRepository.save(student);
	}

	public Student updateLocation(Long studentId, Location location) {

		Student student = getById(studentId);
		student.setLocation(location);

		return studentRepository.save(student);
	}

	public Student updatePreferredSubjects(Long studentId, List<Long> subjectIds) {

		Student student = getById(studentId);

		List<Subject> subjects = subjectRepository.findAllById(subjectIds);

		student.setPreferredSubjects(Set.copyOf(subjects));

		return studentRepository.save(student);
	}

	public void incrementBookings(Long studentId) {

		Student student = getById(studentId);

		int current = student.getTotalBookings() != null ? student.getTotalBookings() : 0;
		student.setTotalBookings(current + 1);

		studentRepository.save(student);
	}

	public List<Student> getAll() {
		return studentRepository.findAll();
	}
}