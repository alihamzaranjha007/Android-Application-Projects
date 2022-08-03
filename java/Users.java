package com.example.classwork;

public class Users {
    private String name;
    private String email;
    private String gender;
    private String uri;

    public Users(){

    }
    public Users(String name, String email, String gender, String uri) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
