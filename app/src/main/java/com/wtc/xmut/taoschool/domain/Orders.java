package com.wtc.xmut.taoschool.domain;

import java.util.Date;

public class Orders {
    private Integer id;

    private Integer shopId;

    private String buyerUsername;

    private String sellerUsername;

    private Integer count;

    private Date time;

    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername == null ? null : buyerUsername.trim();
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername == null ? null : sellerUsername.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}