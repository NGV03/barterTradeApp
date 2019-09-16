package com.example.bartertrade;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String userid;
    private String name;
    private String email;
    private String phone;
    private String token;

    public User(){

    }


    public User(String userid, String name, String email, String phone) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    protected User(Parcel in) {
        userid = in.readString();
        name = in.readString();
        email = in.readString();
        phone = in.readString();
        token = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(token);
        dest.writeString(phone);
    }
}
