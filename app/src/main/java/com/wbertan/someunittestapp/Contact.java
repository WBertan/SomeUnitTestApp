package com.wbertan.someunittestapp;

/**
 * Created by william.bertan on 15/12/2016.
 */

public class Contact {
    private long id;
    private String name;
    private String phone;

    public Contact() {}

    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(").append(getId()).append(") ").append(getName()).append(" - ").append(getPhone());
        return stringBuilder.toString();
    }
}
