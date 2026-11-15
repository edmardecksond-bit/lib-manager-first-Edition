package com.nb.library.model;

public abstract class Item {

    private final String id;
    private final String title;
    private boolean borrowed;

    public Item(String id, String title) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("L'identifiant ne peut pas être vide");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide");
        }
        this.id = id;
        this.title = title;
        this.borrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void borrow() {
        if (borrowed) {
            throw new IllegalStateException("Ce document est déjà emprunté");
        }
        borrowed = true;
    }

    public void returnItem() {
        if (!borrowed) {
            throw new IllegalStateException("Ce document n'est pas emprunté");
        }
        borrowed = false;
    }

    public String describe() {
        String status = borrowed ? "emprunté" : "disponible";
        return "[" + id + "] " + title + " (" + status + ")";
    }

    public abstract int getLoanDurationDays();

}