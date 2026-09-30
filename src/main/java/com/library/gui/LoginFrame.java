package com.library.gui;

import com.library.model.User;
import com.library.service.AuthService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username = new JTextField(20);
    private final JPasswordField password = new JPasswordField(20);
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Library Management System - Login");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);

        g.gridx = 0; g.gridy = 0;
        panel.add(new JLabel("Username:"), g);
        g.gridx = 1;
        panel.add(username, g);

        g.gridx = 0; g.gridy = 1;
        panel.add(new JLabel("Password:"), g);
        g.gridx = 1;
        panel.add(password, g);

        JButton login = new JButton("Login");
        g.gridx = 1; g.gridy = 2;
        panel.add(login, g);

        login.addActionListener(e -> login());

        add(panel);
    }

    private void login() {
        try {
            User user = authService.login(username.getText(), new String(password.getPassword()));
            if (user == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
                return;
            }
            dispose();
            new MainFrame(user).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
