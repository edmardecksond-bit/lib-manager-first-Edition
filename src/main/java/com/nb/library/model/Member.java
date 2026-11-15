package com.nb.library.model;

import java.util.ArrayList;
import java.util.List;

public class Member {

    public static final int MAX_BORROWS = 3;

    private final String name;
    private final List<Item> borrowedItems = new ArrayList<>();

    public Member(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Item> getBorrowedItems() {
        return List.copyOf(borrowedItems);
    }

    public boolean canBorrow() {
        return borrowedItems.size() < MAX_BORROWS;
    }

    public void addBorrowedItem(Item item) {
        borrowedItems.add(item);
    }

    public void removeBorrowedItem(Item item) {
        if (!borrowedItems.remove(item)) {
            throw new IllegalStateException(name + " n'a pas emprunté ce document");
        }
    }
}