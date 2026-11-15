package com.nb.library.service;

import com.nb.library.exception.BorrowLimitExceededException;
import com.nb.library.exception.ItemNotFoundException;
import com.nb.library.model.Book;
import com.nb.library.model.DVD;
import com.nb.library.model.Magazine;
import com.nb.library.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private Member alice;

    @BeforeEach
    void setUp() {
        library = new Library();
        library.addItem(new Book("B001", "Le Petit Prince", "Antoine de Saint-Exupéry"));
        library.addItem(new Book("B002", "1984", "George Orwell"));
        library.addItem(new DVD("D001", "Inception", 148));
        library.addItem(new Magazine("M001", "Science & Vie", 12));
        alice = new Member("Alice");
    }

    @Test
    void shouldBorrowAvailableItem() {
        library.borrowItem(alice, "B001");

        assertTrue(library.findById("B001").isBorrowed());
        assertEquals(1, alice.getBorrowedItems().size());
    }

    @Test
    void shouldThrowExceptionWhenItemNotFound() {
        assertThrows(ItemNotFoundException.class,
                () -> library.borrowItem(alice, "X999"));
    }

    @Test
    void shouldThrowExceptionWhenBorrowLimitReached() {
        library.borrowItem(alice, "B001");
        library.borrowItem(alice, "B002");
        library.borrowItem(alice, "D001");

        assertThrows(BorrowLimitExceededException.class,
                () -> library.borrowItem(alice, "M001"));
    }

    @Test
    void shouldMakeItemAvailableAgainAfterReturn() {
        library.borrowItem(alice, "B001");
        library.returnItem(alice, "B001");

        assertFalse(library.findById("B001").isBorrowed());
        assertTrue(alice.getBorrowedItems().isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenReturningItemNotBorrowed() {
        assertThrows(IllegalStateException.class,
                () -> library.returnItem(alice, "M001"));
    }
}
