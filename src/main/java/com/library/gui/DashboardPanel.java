package com.library.gui;

import com.library.dao.BookDAO;
import com.library.dao.MemberDAO;
import com.library.service.LibraryService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final JLabel totalBooks = new JLabel();
    private final JLabel availableBooks = new JLabel();
    private final JLabel totalMembers = new JLabel();
    private final JLabel activeBorrowings = new JLabel();
    private final JLabel overdueBooks = new JLabel();
    private final JLabel todayReturns = new JLabel();

    public DashboardPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Library Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel stats = new JPanel(new GridLayout(2, 3, 10, 10));
        stats.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        addCard(stats, "Total Books", totalBooks);
        addCard(stats, "Available Books", availableBooks);
        addCard(stats, "Total Members", totalMembers);
        addCard(stats, "Active Borrowings", activeBorrowings);
        addCard(stats, "Overdue Books", overdueBooks);
        addCard(stats, "Today's Returns", todayReturns);

        add(stats, BorderLayout.CENTER);

        JButton refresh = new JButton("Refresh");
        refresh.addActionListener(e -> loadData());
        add(refresh, BorderLayout.SOUTH);

        loadData();
    }

    private void addCard(JPanel panel, String title, JLabel value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        value.setHorizontalAlignment(SwingConstants.CENTER);
        value.setFont(new Font("Arial", Font.BOLD, 24));
        card.add(t, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        panel.add(card);
    }

    private void loadData() {
        try {
            BookDAO b = new BookDAO();
            MemberDAO m = new MemberDAO();
            LibraryService l = new LibraryService();

            totalBooks.setText(String.valueOf(b.count()));
            availableBooks.setText(String.valueOf(b.availableCount()));
            totalMembers.setText(String.valueOf(m.count()));
            activeBorrowings.setText(String.valueOf(l.activeCount()));
            overdueBooks.setText(String.valueOf(l.overdueCount()));
            todayReturns.setText(String.valueOf(l.todayReturns()));
        } catch (Exception e) {
            totalBooks.setText("0");
            availableBooks.setText("0");
            totalMembers.setText("0");
            activeBorrowings.setText("0");
            overdueBooks.setText("0");
            todayReturns.setText("0");
        }
    }
}
