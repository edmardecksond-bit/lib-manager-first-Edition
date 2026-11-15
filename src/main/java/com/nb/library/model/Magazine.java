package com.nb.library.model;

public class Magazine extends Item {

    private final int issueNumber;

    public Magazine(String id, String title, int issueNumber) {
        super(id, title);
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Le numéro de magazine doit être positif");
        }
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String describe() {
        return "Magazine : " + super.describe() + " - " + issueNumber;
    }

    @Override
    public int getLoanDurationDays() {
        return 14;
    }

}
