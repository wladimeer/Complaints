package com.example.complaints.model;

public class User {
    private String id;
    private String email;
    private String name;
    private String cellPhone;
    private String password;

    public User() {}

    public User(String id, String email, String name, String cellPhone, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.cellPhone = cellPhone;
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

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}