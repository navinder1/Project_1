package com.project.tutorplatform.dto.response;

import java.util.Set;

import com.project.tutorplatform.enums.TeachingMode;

public class StudentResponse {

    private Long id;
    private Long userId;
    private String gradeLevel;
    private Set<Long> preferredSubjectIds;
    private TeachingMode preferredMode;
    private String parentName;
    private String parentPhone;
    private Integer totalBookings;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; }

    public Set<Long> getPreferredSubjectIds() { return preferredSubjectIds; }
    public void setPreferredSubjectIds(Set<Long> preferredSubjectIds) { this.preferredSubjectIds = preferredSubjectIds; }

    public TeachingMode getPreferredMode() { return preferredMode; }
    public void setPreferredMode(TeachingMode preferredMode) { this.preferredMode = preferredMode; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public Integer getTotalBookings() { return totalBookings; }
    public void setTotalBookings(Integer totalBookings) { this.totalBookings = totalBookings; }
}