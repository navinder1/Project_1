package com.project.tutorplatform.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UserStore {

    private final Map<String, Map<String, Object>> users = new HashMap<>();

    public Map<String, Object> getUser(String key) {
        return users.get(key);
    }

    public void saveUser(String key, Map<String, Object> user) {
        users.put(key, user);
    }
}