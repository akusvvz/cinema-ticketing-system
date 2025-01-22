package com.cinema.entities;

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

    @Override
    public String toString() {
        return "Halls{" +
                "hallId=" + hallId +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
