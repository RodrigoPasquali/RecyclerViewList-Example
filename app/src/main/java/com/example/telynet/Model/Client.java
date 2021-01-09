package com.example.telynet.Model;

public class Client {
    private String name;
    private String code;
    private String phone;
    private String email;
    private String visit;

    public Client(String name, String code, String phone, String email, String visit) {
        this.name = name;
        this.code = code;
        this.phone = phone;
        this.email = email;
        this.visit = visit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }
}
