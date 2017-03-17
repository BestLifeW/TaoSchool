package com.wtc.xmut.taoschool.domain;

public class Shop {
    private Integer id;

    private String shopname;

    private String description;

    private String category;

    private String picture;

    private String price;

    private String username;

    private String oldprice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getOldprice() {
        return oldprice;
    }

    public void setOldprice(String oldprice) {
        this.oldprice = oldprice == null ? null : oldprice.trim();
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", shopname='" + shopname + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", picture='" + picture + '\'' +
                ", price='" + price + '\'' +
                ", username='" + username + '\'' +
                ", oldprice='" + oldprice + '\'' +
                '}';
    }
}