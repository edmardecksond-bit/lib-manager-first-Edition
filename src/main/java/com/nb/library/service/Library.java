package com.nb.library.service;

import com.nb.library.exception.ItemNotFoundException;
import com.nb.library.exception.BorrowLimitExceededException;
import com.nb.library.model.Item;
import com.nb.library.model.Member;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Le document ne peut pas être null");
        }
        items.add(item);
    }

    public Item findById(String id) {
        for (Item item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        throw new ItemNotFoundException(id);
    }

    public List<Item> getItems() {
        return List.copyOf(items);
    }

    public void displayCatalog() {
        for (Item item : items) {
            System.out.println(item.describe());
        }
    }

    public void borrowItem(Member member, String itemId) {
        Item item = findById(itemId);
        /* Check the limit before borrowing, so the item is never marked as
         borrowed if the member cannot receive it*/
        if (!member.canBorrow()) {
            throw new BorrowLimitExceededException(member.getName(), Member.MAX_BORROWS);
        }

        item.borrow();
        member.addBorrowedItem(item);
    }

    public void returnItem(Member member, String itemId) {
        Item item = findById(itemId);

        /*
        * Order matters: remove from the member first. If the member never
        *borrowed this item, the exception stops us before the item's state changes.
        * */

        member.removeBorrowedItem(item);
        item.returnItem();
    }
}