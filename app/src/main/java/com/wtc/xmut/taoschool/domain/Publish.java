package com.wtc.xmut.taoschool.domain;

import java.util.Date;

public class Publish {
    private Integer id;

    private String userName;

    private Integer shopId;

    private Date time;

    private Integer likecount;

    private Integer isreserve;

    private Integer ispay;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    public Integer getIsreserve() {
        return isreserve;
    }

    public void setIsreserve(Integer isreserve) {
        this.isreserve = isreserve;
    }

    public Integer getIspay() {
        return ispay;
    }

    public void setIspay(Integer ispay) {
        this.ispay = ispay;
    }
}