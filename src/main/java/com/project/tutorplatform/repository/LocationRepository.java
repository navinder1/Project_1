package com.project.tutorplatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tutorplatform.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByTutorId(Long tutorId);

    List<Location> findByCityIgnoreCase(String city);
}