package com.library.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LibraryServiceTest {

    @Test
    void invalidDuePeriodIsRejected() {
        LibraryService service = new LibraryService();

        assertThrows(IllegalArgumentException.class,
                () -> service.borrow(1, 1, 0));
    }
}
