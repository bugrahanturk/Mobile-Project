package com.example.tatil.Classes;

public class User {

    private String name;
    private String email;
    private String username;
    private String adress;
    private String imageurl;
    private String id;
    private String mentor;

    public User() {
    }

    public User(String name, String email, String username, String adress, String imageurl, String id,String mentor) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.adress = adress;
        this.imageurl = imageurl;
        this.id = id;
        this.mentor = mentor;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
