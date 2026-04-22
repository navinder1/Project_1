package com.project.tutorplatform.entity;
//package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.nimbusds.oauth2.sdk.id.Subject;
import com.nimbusds.oauth2.sdk.rar.Location;
import com.project.tutorplatform.dto.enums.SubscriptionPlan;
import com.project.tutorplatform.dto.enums.TeachingMode;
//import com.project.tutorplatform.entity.Availability;
//import com.project.tutorplatform.entity.FileMetadata;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutors")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Linked User
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(length = 2000)
    private String bio;

    @Column(length = 1000)
    private String qualifications;

    @Column
    private Integer experience;

    @Column(precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    // 📚 Subjects
    @ManyToMany
    @JoinTable(
        name = "tutor_subjects",
        joinColumns = @JoinColumn(name = "tutor_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;

    // 🎓 Teaching Mode
    @Enumerated(EnumType.STRING)
    @Column
    private TeachingMode teachingMode;

    // ⭐ Rating
    @Column
    private Double rating;

    @Column
    private Integer totalReviews = 0;

    // 🟢 Availability & Approval
    @Column
    private Boolean isAvailable = true;

    @Column
    private Boolean isApproved = false;

    // 🗓 Availability Slots
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    private List<Availability> availabilitySlots;

    // 📍 Location
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    // 📄 Documents
    @OneToMany(mappedBy = "tutor")
    private List<FileMetadata> documents;

    // 💳 Subscription
    @Enumerated(EnumType.STRING)
    private SubscriptionPlan subscriptionPlan;

    // 💰 Wallet
    @Column(precision = 12, scale = 2)
    private BigDecimal walletBalance = BigDecimal.ZERO;

    // ===== Constructors =====
    public Tutor() {}

    // ===== Getters & Setters =====
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getQualifications() { return qualifications; }
    public void setQualifications(String qualifications) { this.qualifications = qualifications; }

    public Integer getExperience() { return experience; }
    public void setExperience(Integer experience) { this.experience = experience; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public Set<Subject> getSubjects() { return subjects; }
    public void setSubjects(Set<Subject> subjects) { this.subjects = subjects; }

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

    public List<Availability> getAvailabilitySlots() { return availabilitySlots; }
    public void setAvailabilitySlots(List<Availability> availabilitySlots) { this.availabilitySlots = availabilitySlots; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public List<FileMetadata> getDocuments() { return documents; }
    public void setDocuments(List<FileMetadata> documents) { this.documents = documents; }

    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) { this.subscriptionPlan = subscriptionPlan; }

    public BigDecimal getWalletBalance() { return walletBalance; }
    public void setWalletBalance(BigDecimal walletBalance) { this.walletBalance = walletBalance; }
}

