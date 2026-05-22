package com.project.tutorplatform.mapper;

import com.project.tutorplatform.dto.response.StudentResponse;
import com.project.tutorplatform.entity.User;

public class UserMapper {

    public static StudentResponse toResponse(User user) {

        if (user == null) return null;

        StudentResponse res = new StudentResponse();

        res.setUserId(user.getId());
        res.setParentName(user.getUsername());
        res.setEmail(user.getEmail());
        res.setParentName(user.getFullName());
        res.setPhoneNumber(user.getPhoneNumber());
        res.setIsActive(user.getIsActive());
        res.setIsVerified(user.getIsVerified());

        return res;
    }
}