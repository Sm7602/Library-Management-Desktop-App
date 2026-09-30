package com.library.service;

import com.library.model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BookServiceTest {

    @Test
    void invalidBookIsRejected() {
        BookService service = new BookService();
        Book book = new Book("", "", "", "", 0, 0);

        assertThrows(IllegalArgumentException.class, () -> service.add(book));
    }
}
