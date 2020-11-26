package com.example.complaints.model;

public class User {
    private String id;
    private String email;
    private String name;
    private String cellphone;
    private String password;

    public User() {}

    public User(String id, String email, String name, String cellphone, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.cellphone = cellphone;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}