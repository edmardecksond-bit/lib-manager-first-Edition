package com.nb.library;

import com.nb.library.model.Item;
import com.nb.library.model.Book;
import com.nb.library.model.DVD;
import com.nb.library.model.Magazine;
import com.nb.library.service.Library;
import com.nb.library.model.Member;
import com.nb.library.exception.BorrowLimitExceededException;
import com.nb.library.exception.ItemNotFoundException;
import com.nb.library.utils.ItemCsvLoader;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        ItemCsvLoader loader = new ItemCsvLoader();
        for (Item item : loader.load("items.csv")) {
            library.addItem(item);
        }

        library.displayCatalog();

        Member alice = new Member("Alice");

        library.borrowItem(alice, "B001");
        library.borrowItem(alice, "B002");
        library.borrowItem(alice, "D001");
        library.displayCatalog();

        try {
            library.borrowItem(alice, "M001");
        } catch (BorrowLimitExceededException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        try {
            library.borrowItem(alice, "X999");
        } catch (ItemNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        library.returnItem(alice, "B001");
        library.displayCatalog();

        try {
            library.returnItem(alice, "M001"); // Alice n'a jamais emprunté M001
        } catch (IllegalStateException e) {
            System.out.println("Erreur : " + e.getMessage());
        }


    }
}