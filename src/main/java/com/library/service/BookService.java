package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.util.ValidationUtils;

import java.util.List;

public class BookService {
    private final BookDAO dao = new BookDAO();

    public boolean add(Book book) {
        validate(book);
        return dao.add(book);
    }

    public boolean update(Book book) {
        validate(book);
        if (book.getAvailable() < 0 || book.getAvailable() > book.getQuantity()) {
            throw new IllegalArgumentException("Available quantity is invalid.");
        }
        return dao.update(book);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Book> findAll() {
        return dao.findAll();
    }

    public List<Book> search(String keyword, String type) {
        return dao.search(keyword, type);
    }

    public int count() {
        return dao.count();
    }

    public int availableCount() {
        return dao.availableCount();
    }

    private void validate(Book b) {
        ValidationUtils.required(b.getIsbn(), "ISBN");
        ValidationUtils.required(b.getTitle(), "Title");
        ValidationUtils.required(b.getAuthor(), "Author");
        ValidationUtils.required(b.getGenre(), "Genre");
        ValidationUtils.positive(b.getYear(), "Year");
        ValidationUtils.positive(b.getQuantity(), "Quantity");
    }
}
