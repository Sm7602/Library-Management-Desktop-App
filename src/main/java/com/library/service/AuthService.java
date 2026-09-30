package com.library.service;

import com.library.dao.UserDAO;
import com.library.model.User;

public class AuthService {
    private final UserDAO dao = new UserDAO();

    public User login(String username, String password) {
        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username and password are required.");
        }
        return dao.authenticate(username, password);
    }
}
