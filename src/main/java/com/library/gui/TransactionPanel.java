package com.library.gui;

import com.library.model.LibraryTransaction;
import com.library.service.LibraryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionPanel extends JPanel {
    private final LibraryService service = new LibraryService();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","Book","Member","Borrow Date","Due Date","Return Date","Status"},0);
    private final JTable table = new JTable(model);

    public TransactionPanel() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        JButton borrow = new JButton("Borrow Book");
        JButton returnBook = new JButton("Return Book");
        JButton refresh = new JButton("Refresh");

        top.add(borrow); top.add(returnBook); top.add(refresh);
        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        borrow.addActionListener(e -> borrow());
        returnBook.addActionListener(e -> returnBook());
        refresh.addActionListener(e -> load());

        load();
    }

    private void borrow() {
        JTextField bookId = new JTextField();
        JTextField memberId = new JTextField();
        JTextField days = new JTextField("14");

        Object[] fields = {"Book ID",bookId,"Member ID",memberId,"Due in days",days};

        if(JOptionPane.showConfirmDialog(this,fields,"Borrow Book",
                JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION) {
            try {
                boolean result = service.borrow(Integer.parseInt(bookId.getText()),
                        Integer.parseInt(memberId.getText()),Integer.parseInt(days.getText()));
                if(!result) {
                    JOptionPane.showMessageDialog(this,"Book is not available.");
                    return;
                }
                load();
            } catch(Exception e) { error(e); }
        }
    }

    private void returnBook() {
        int row = table.getSelectedRow();
        if(row < 0) { error("Select a transaction."); return; }

        try {
            boolean result = service.returnBook((int)model.getValueAt(row,0));
            if(!result) {
                error("Transaction cannot be returned.");
                return;
            }
            load();
        } catch(Exception e) { error(e); }
    }

    private void load() {
        model.setRowCount(0);
        for(LibraryTransaction t:service.findAll()) {
            model.addRow(new Object[]{t.getId(),t.getBookTitle(),t.getMemberName(),
                    t.getBorrowDate(),t.getDueDate(),t.getReturnDate(),t.getStatus()});
        }
    }

    private void error(Exception e) { error(e.getMessage()); }
    private void error(String message) {
        JOptionPane.showMessageDialog(this,message,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
