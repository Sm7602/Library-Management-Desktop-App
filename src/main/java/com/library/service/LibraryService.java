package com.library.service;

import com.library.dao.TransactionDAO;
import com.library.model.LibraryTransaction;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {
    private final TransactionDAO dao = new TransactionDAO();

    public boolean borrow(int bookId, int memberId, int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Due period must be greater than zero.");
        }
        return dao.borrow(bookId, memberId, LocalDate.now().plusDays(days));
    }

    public boolean returnBook(int transactionId) {
        return dao.returnBook(transactionId);
    }

    public List<LibraryTransaction> findAll() {
        return dao.findAll();
    }

    public int activeCount() {
        return dao.activeCount();
    }

    public int overdueCount() {
        return dao.overdueCount();
    }

    public int todayReturns() {
        return dao.todayReturns();
    }
}
