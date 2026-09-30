package com.library.dao;

import com.library.model.User;
import com.library.util.DatabaseConnection;

import java.sql.*;

public class UserDAO {
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, username);
            p.setString(2, password);
            ResultSet rs = p.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("role"));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
