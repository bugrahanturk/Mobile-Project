package com.example.tatil.Classes;

public class Post {

    private String description;
    private String imageUrl;
    private   String location;
    private String postId;
    private String publisher;

    public Post() {
    }

    public Post(String description, String imageurl, String postid, String publisher,String location) {
        this.description = description;
        this.imageUrl = imageurl;
        this.postId = postId;
        this.publisher = publisher;
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageUrl;
    }

    public void setImageurl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostid() {
        return postId;
    }

    public void setPostid(String postId) {
        this.postId = postId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
