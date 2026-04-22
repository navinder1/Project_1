package com.project.tutorplatform.entity;

//package com.example.demo.entity;

import java.util.Set;

import com.nimbusds.oauth2.sdk.id.Subject;
import com.nimbusds.oauth2.sdk.rar.Location;
import com.project.tutorplatform.dto.enums.TeachingMode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Linked User Account
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    // 🎓 Academic Info
    @Column(length = 50)
    private String gradeLevel;

    // 📚 Preferred Subjects
    @ManyToMany
    @JoinTable(
        name = "student_subjects",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> preferredSubjects;

    // 🎓 Preferred Mode
    @Enumerated(EnumType.STRING)
    private TeachingMode preferredMode;

    // 📍 Location
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    // 👨‍👩‍👧 Parent Details (Optional)
    @Column(length = 100)
    private String parentName;

    @Column(length = 15)
    private String parentPhone;

    // 📊 Analytics
    @Column
    private Integer totalBookings = 0;

    // ===== Constructors =====
    public Student() {}

    // ===== Getters & Setters =====
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }

    public Set<Subject> getPreferredSubjects() { return preferredSubjects; }
    public void setPreferredSubjects(Set<Subject> preferredSubjects) { this.preferredSubjects = preferredSubjects; }

    public TeachingMode getPreferredMode() { return preferredMode; }
    public void setPreferredMode(TeachingMode preferredMode) { this.preferredMode = preferredMode; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public Integer getTotalBookings() { return totalBookings; }
    public void setTotalBookings(Integer totalBookings) { this.totalBookings = totalBookings; }
}