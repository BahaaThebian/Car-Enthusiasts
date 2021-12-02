package com.lau.carenthusiasts;

public class SaleHelperClass {
    String description;
    String imgurl;
    String price;

    public SaleHelperClass(String description, String imgURL, String price) {
        this.description = description;
        this.imgurl = imgURL;
        this.price = price;
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
        return imgurl;
    }

    public void setImgURL(String imgURL) {
        this.imgurl = imgURL;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
