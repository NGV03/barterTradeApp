package com.example.bartertrade;

import android.graphics.Bitmap;

public class Model {
    String id,title,location,Img;

    public Model (){
        //empty  constructor needed
    }

    public Model(String id, String title, String location, String url) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.Img = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
   public String getUrl() {
        return Img;
    }

    public void setUrl(String url) {
        this.Img = url;
    }
}
