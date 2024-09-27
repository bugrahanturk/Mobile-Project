package com.example.tatil.Classes;

public class HashTag {

    private String postid;
    private String tag;

    public HashTag() {
    }

    public HashTag(String postid, String tag) {
        this.postid = postid;
        this.tag = tag;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
