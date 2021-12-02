package com.lau.carenthusiasts;

public class SaleHelperClass {
    String description;
    String imgURL;
    String price;
    String phoneNo;

    public SaleHelperClass(String description, String imgURL, String price, String phoneNo) {
        this.description = description;
        this.imgURL = imgURL;
        this.price = price;
        this.phoneNo = phoneNo;
    }

    public SaleHelperClass() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
