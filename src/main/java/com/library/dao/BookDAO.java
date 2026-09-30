package com.library.dao;

import com.library.model.Book;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public boolean add(Book book) {
        String sql = "INSERT INTO books(isbn,title,author,genre,year,quantity,available) VALUES(?,?,?,?,?,?,?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, book.getIsbn());
            p.setString(2, book.getTitle());
            p.setString(3, book.getAuthor());
            p.setString(4, book.getGenre());
            p.setInt(5, book.getYear());
            p.setInt(6, book.getQuantity());
            p.setInt(7, book.getAvailable());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Book book) {
        String sql = "UPDATE books SET isbn=?,title=?,author=?,genre=?,year=?,quantity=?,available=? WHERE id=?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, book.getIsbn());
            p.setString(2, book.getTitle());
            p.setString(3, book.getAuthor());
            p.setString(4, book.getGenre());
            p.setInt(5, book.getYear());
            p.setInt(6, book.getQuantity());
            p.setInt(7, book.getAvailable());
            p.setInt(8, book.getId());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement("DELETE FROM books WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findAll() {
        return search("", "title");
    }

    public List<Book> search(String keyword, String type) {
        List<Book> books = new ArrayList<>();
        String column = switch (type.toLowerCase()) {
            case "author" -> "author";
            case "isbn" -> "isbn";
            case "genre" -> "genre";
            default -> "title";
        };
        String sql = "SELECT * FROM books WHERE " + column + " LIKE ? ORDER BY id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, "%" + keyword + "%");
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"), rs.getString("isbn"), rs.getString("title"),
                        rs.getString("author"), rs.getString("genre"), rs.getInt("year"),
                        rs.getInt("quantity"), rs.getInt("available")));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() {
        return countQuery("SELECT COUNT(*) FROM books");
    }

    public int availableCount() {
        return countQuery("SELECT COALESCE(SUM(available),0) FROM books");
    }

    private int countQuery(String sql) {
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
