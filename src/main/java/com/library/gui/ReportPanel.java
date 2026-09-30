package com.library.gui;

import com.library.service.ReportService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportPanel extends JPanel {
    private final ReportService service = new ReportService();
    private final DefaultTableModel model = new DefaultTableModel();

    public ReportPanel() {
        setLayout(new BorderLayout());

        JPanel buttons = new JPanel(new FlowLayout());
        JButton popular = new JButton("Popular Books");
        JButton overdue = new JButton("Overdue Books");
        JButton activity = new JButton("Member Activity");

        buttons.add(popular);
        buttons.add(overdue);
        buttons.add(activity);

        add(buttons,BorderLayout.NORTH);
        add(new JScrollPane(new JTable(model)),BorderLayout.CENTER);

        popular.addActionListener(e -> showPopular());
        overdue.addActionListener(e -> showOverdue());
        activity.addActionListener(e -> showActivity());
    }

    private void showPopular() {
        model.setDataVector(service.popularBooks().toArray(new Object[0][]),
                new Object[]{"Book","Borrowings"});
    }

    private void showOverdue() {
        model.setDataVector(service.overdueBooks().toArray(new Object[0][]),
                new Object[]{"Book","Member","Due Date"});
    }

    private void showActivity() {
        model.setDataVector(service.memberActivity().toArray(new Object[0][]),
                new Object[]{"Member","Books Borrowed"});
    }
}
