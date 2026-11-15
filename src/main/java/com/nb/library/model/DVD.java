package com.nb.library.model;

public class DVD extends Item {

    private final int durationMinutes;

    public DVD(String id, String title, int durationMinutes) {
        super(id, title);

        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("La durée doit être positive");
        }
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    @Override
    public String describe() {
        return "DVD : " + super.describe() + " - " + durationMinutes + " min";
    }

    @Override
    public int getLoanDurationDays() {
        return 7;
    }
}