package com.example.bartertrade;

public class Item {
    String title;
    String shortDesc;
    String location;
    String url;
    String cate;
    String id;

    public Item(String title, String shortDesc, String location, String url, String cate, String id) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.location = location;
        this.url = url;
        this.cate = cate;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
