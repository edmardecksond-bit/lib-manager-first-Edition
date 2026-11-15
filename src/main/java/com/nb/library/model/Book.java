package com.nb.library.model;

public class Book extends Item {

    private final String author;

    public Book(String id, String title, String author) {
        super(id, title);
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("L'auteur ne peut pas être vide");
        }
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String describe() {
        return "Livre : " + super.describe() + " de " + author;
    }

    @Override
    public int getLoanDurationDays() {
        return 21;
    }
}