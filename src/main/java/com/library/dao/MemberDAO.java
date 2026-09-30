package com.library.dao;

import com.library.model.Member;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    public boolean add(Member m) {
        String sql = "INSERT INTO members(name,email,phone,address,status,member_since) VALUES(?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, m.getName());
            p.setString(2, m.getEmail());
            p.setString(3, m.getPhone());
            p.setString(4, m.getAddress());
            p.setString(5, m.getStatus());
            p.setDate(6, Date.valueOf(m.getMemberSince()));
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Member m) {
        String sql = "UPDATE members SET name=?,email=?,phone=?,address=?,status=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, m.getName());
            p.setString(2, m.getEmail());
            p.setString(3, m.getPhone());
            p.setString(4, m.getAddress());
            p.setString(5, m.getStatus());
            p.setInt(6, m.getId());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement("DELETE FROM members WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members ORDER BY id";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                members.add(map(rs));
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Member> search(String keyword) {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE name LIKE ? OR email LIKE ? OR phone LIKE ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            String term = "%" + keyword + "%";
            p.setString(1, term);
            p.setString(2, term);
            p.setString(3, term);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                members.add(map(rs));
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM members");
             ResultSet rs = p.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Member map(ResultSet rs) throws SQLException {
        return new Member(
                rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                rs.getString("phone"), rs.getString("address"),
                rs.getString("status"), rs.getDate("member_since").toLocalDate());
    }
}
