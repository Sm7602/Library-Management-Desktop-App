package com.library.gui;

import com.library.model.Book;
import com.library.service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {
    private final BookService service = new BookService();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID","ISBN","Title","Author","Genre","Year","Quantity","Available"}, 0);
    private final JTable table = new JTable(model);
    private final JTextField search = new JTextField(20);
    private final JComboBox<String> type =
            new JComboBox<>(new String[]{"Title","Author","ISBN","Genre"});

    public BookPanel() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        JButton add = new JButton("Add Book");
        JButton edit = new JButton("Edit Book");
        JButton delete = new JButton("Delete Book");
        JButton refresh = new JButton("Refresh");
        JButton find = new JButton("Search");

        top.add(add); top.add(edit); top.add(delete); top.add(refresh);
        top.add(new JLabel("Search:")); top.add(search); top.add(type); top.add(find);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> addBook());
        edit.addActionListener(e -> editBook());
        delete.addActionListener(e -> deleteBook());
        refresh.addActionListener(e -> load(service.findAll()));
        find.addActionListener(e -> load(service.search(search.getText(), type.getSelectedItem().toString())));

        load(service.findAll());
    }

    private void addBook() {
        JTextField isbn = new JTextField();
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextField genre = new JTextField();
        JTextField year = new JTextField();
        JTextField quantity = new JTextField();

        Object[] fields = {"ISBN", isbn, "Title", title, "Author", author,
                "Genre", genre, "Year", year, "Quantity", quantity};

        if (JOptionPane.showConfirmDialog(this, fields, "Add Book",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                service.add(new Book(isbn.getText(), title.getText(), author.getText(),
                        genre.getText(), Integer.parseInt(year.getText()),
                        Integer.parseInt(quantity.getText())));
                load(service.findAll());
                JOptionPane.showMessageDialog(this, "Book added successfully.");
            } catch (Exception e) {
                error(e);
            }
        }
    }

    private void editBook() {
        int row = table.getSelectedRow();
        if (row < 0) {
            error("Select a book.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        JTextField isbn = new JTextField(model.getValueAt(row,1).toString());
        JTextField title = new JTextField(model.getValueAt(row,2).toString());
        JTextField author = new JTextField(model.getValueAt(row,3).toString());
        JTextField genre = new JTextField(model.getValueAt(row,4).toString());
        JTextField year = new JTextField(model.getValueAt(row,5).toString());
        JTextField quantity = new JTextField(model.getValueAt(row,6).toString());
        JTextField available = new JTextField(model.getValueAt(row,7).toString());

        Object[] fields = {"ISBN",isbn,"Title",title,"Author",author,"Genre",genre,
                "Year",year,"Quantity",quantity,"Available",available};

        if (JOptionPane.showConfirmDialog(this, fields, "Edit Book",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                service.update(new Book(id,isbn.getText(),title.getText(),author.getText(),
                        genre.getText(),Integer.parseInt(year.getText()),
                        Integer.parseInt(quantity.getText()),Integer.parseInt(available.getText())));
                load(service.findAll());
            } catch (Exception e) {
                error(e);
            }
        }
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row < 0) {
            error("Select a book.");
            return;
        }
        try {
            service.delete((int) model.getValueAt(row,0));
            load(service.findAll());
        } catch (Exception e) {
            error(e);
        }
    }

    private void load(List<Book> books) {
        model.setRowCount(0);
        for (Book b : books) {
            model.addRow(new Object[]{b.getId(),b.getIsbn(),b.getTitle(),b.getAuthor(),
                    b.getGenre(),b.getYear(),b.getQuantity(),b.getAvailable()});
        }
    }

    private void error(Exception e) {
        error(e.getMessage());
    }

    private void error(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
