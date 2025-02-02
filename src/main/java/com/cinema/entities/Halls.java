package com.cinema.entities;

// represents a cinema hall entity
public class Halls {
    private int hallId;
    private String name;
    private int capacity;

    public Halls() {}

    public Halls(int hallId, String name, int capacity) {
        this.hallId = hallId;
        this.name = name;
        this.capacity = capacity;
    }

    //  getters and setters
    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // returns the hall name as a string representation
    @Override
    public String toString() {
        return name;
    }
}
