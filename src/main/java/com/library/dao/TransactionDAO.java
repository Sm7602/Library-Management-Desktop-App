package com.library.dao;

import com.library.model.LibraryTransaction;
import com.library.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean borrow(int bookId, int memberId, LocalDate dueDate) {
        String updateBook = "UPDATE books SET available=available-1 WHERE id=? AND available>0";
        String insert = "INSERT INTO transactions(book_id,member_id,borrow_date,due_date,status) VALUES(?,?,?,?,?)";

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement b = c.prepareStatement(updateBook);
                 PreparedStatement t = c.prepareStatement(insert)) {

                b.setInt(1, bookId);
                if (b.executeUpdate() == 0) {
                    c.rollback();
                    return false;
                }

                t.setInt(1, bookId);
                t.setInt(2, memberId);
                t.setDate(3, Date.valueOf(LocalDate.now()));
                t.setDate(4, Date.valueOf(dueDate));
                t.setString(5, "BORROWED");
                t.executeUpdate();

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean returnBook(int transactionId) {
        String find = "SELECT book_id FROM transactions WHERE id=? AND status='BORROWED'";
        String updateTransaction = "UPDATE transactions SET return_date=?,status='RETURNED' WHERE id=?";
        String updateBook = "UPDATE books SET available=available+1 WHERE id=?";

        try (Connection c = DatabaseConnection.getConnection()) {
            c.setAutoCommit(false);
            try (PreparedStatement f = c.prepareStatement(find)) {
                f.setInt(1, transactionId);
                ResultSet rs = f.executeQuery();
                if (!rs.next()) {
                    c.rollback();
                    return false;
                }

                int bookId = rs.getInt("book_id");

                try (PreparedStatement t = c.prepareStatement(updateTransaction);
                     PreparedStatement b = c.prepareStatement(updateBook)) {
                    t.setDate(1, Date.valueOf(LocalDate.now()));
                    t.setInt(2, transactionId);
                    t.executeUpdate();

                    b.setInt(1, bookId);
                    b.executeUpdate();

                    c.commit();
                    return true;
                }
            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LibraryTransaction> findAll() {
        List<LibraryTransaction> list = new ArrayList<>();
        String sql = """
                SELECT t.*, b.title, m.name
                FROM transactions t
                JOIN books b ON b.id=t.book_id
                JOIN members m ON m.id=t.member_id
                ORDER BY t.id DESC
                """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {
            while (rs.next()) {
                LibraryTransaction t = new LibraryTransaction();
                t.setId(rs.getInt("id"));
                t.setBookId(rs.getInt("book_id"));
                t.setMemberId(rs.getInt("member_id"));
                t.setBookTitle(rs.getString("title"));
                t.setMemberName(rs.getString("name"));
                t.setBorrowDate(rs.getDate("borrow_date").toLocalDate());
                t.setDueDate(rs.getDate("due_date").toLocalDate());
                if (rs.getDate("return_date") != null) {
                    t.setReturnDate(rs.getDate("return_date").toLocalDate());
                }
                t.setStatus(rs.getString("status"));
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int activeCount() {
        return count("SELECT COUNT(*) FROM transactions WHERE status='BORROWED'");
    }

    public int overdueCount() {
        return count("SELECT COUNT(*) FROM transactions WHERE status='BORROWED' AND due_date<CURRENT_DATE");
    }

    public int todayReturns() {
        return count("SELECT COUNT(*) FROM transactions WHERE return_date=CURRENT_DATE");
    }

    private int count(String sql) {
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
