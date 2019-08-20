package com.example.bartertrade;

public class Upload {
    private String mImageUrl;
    private String mCategory;
    private String mTitle;
    private String mShortDescription;
    private String mLocation;

    public Upload() {
        //empty constructor needed
    }

    public Upload(String ImageUrl, String Category, String Title, String ShortDescription, String Location) {
        mImageUrl = ImageUrl;
        mCategory = Category;
        mTitle = Title;
        mShortDescription = ShortDescription;
        mLocation = Location;

    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        mImageUrl = ImageUrl;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String Category) {
        mCategory = Category;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
         mTitle = Title;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String ShortDescription) {
        mShortDescription = ShortDescription;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String Location) {
        mLocation = Location;
    }
}
