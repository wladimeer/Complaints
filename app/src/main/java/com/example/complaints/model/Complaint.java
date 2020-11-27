package com.example.complaints.model;

public class Complaint {
    private String id;
    private String name;
    private String address;
    private boolean state;

    public Complaint() {}

    public Complaint(String id, String name, String address, boolean state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}