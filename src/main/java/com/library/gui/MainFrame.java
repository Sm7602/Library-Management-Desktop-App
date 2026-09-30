package com.library.gui;

import com.library.model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(User user) {
        setTitle("Library Management System");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", new DashboardPanel());
        tabs.addTab("Books", new BookPanel());
        tabs.addTab("Members", new MemberPanel());
        tabs.addTab("Transactions", new TransactionPanel());
        tabs.addTab("Reports", new ReportPanel());

        add(tabs, BorderLayout.CENTER);

        JLabel status = new JLabel("Logged in: " + user.getUsername() + " | Role: " + user.getRole());
        add(status, BorderLayout.SOUTH);
    }
}
