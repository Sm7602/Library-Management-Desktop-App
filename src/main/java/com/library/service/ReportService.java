package com.library.service;

import com.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportService {

    public List<String[]> popularBooks() {
        List<String[]> rows = new ArrayList<>();
        String sql = """
                SELECT b.title, COUNT(t.id) AS borrowings
                FROM transactions t
                JOIN books b ON b.id=t.book_id
                GROUP BY b.id, b.title
                ORDER BY borrowings DESC
                """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                rows.add(new String[]{rs.getString("title"), rs.getString("borrowings")});
            }
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String[]> overdueBooks() {
        List<String[]> rows = new ArrayList<>();
        String sql = """
                SELECT b.title, m.name, t.due_date
                FROM transactions t
                JOIN books b ON b.id=t.book_id
                JOIN members m ON m.id=t.member_id
                WHERE t.status='BORROWED' AND t.due_date<CURRENT_DATE
                ORDER BY t.due_date
                """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                rows.add(new String[]{
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getDate("due_date").toString()
                });
            }
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String[]> memberActivity() {
        List<String[]> rows = new ArrayList<>();
        String sql = """
                SELECT m.name, COUNT(t.id) AS borrowed
                FROM members m
                LEFT JOIN transactions t ON t.member_id=m.id
                GROUP BY m.id, m.name
                ORDER BY borrowed DESC
                """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                rows.add(new String[]{rs.getString("name"), rs.getString("borrowed")});
            }
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
