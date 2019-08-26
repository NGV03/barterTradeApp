package com.example.bartertrade;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Item implements Parcelable{
    private String title;
    private String shortDesc;
    private String location;
    private String url;
    private String cate;
    private String id;

    public Item(String title, String shortDesc, String location, String url, String cate, String id) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.location = location;
        this.url = url;
        this.cate = cate;
        this.id = id;
    }

    public Item(){

    }

    protected Item(Parcel in){
        title = in.readString();
        shortDesc = in.readString();
        location = in.readString();
        url = in.readString();
        cate = in.readString();
        id = in.readString();
    }

    public static  final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(shortDesc);
        parcel.writeString(location);
        parcel.writeString(url);
        parcel.writeString(cate);
        parcel.writeString(id);

    }
}
