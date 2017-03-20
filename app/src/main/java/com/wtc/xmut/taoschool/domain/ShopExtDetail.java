package com.wtc.xmut.taoschool.domain;

/**
 * Created by tianchao on 17-3-19.
 */

public class ShopExtDetail extends Shop{


    private String username;
    private String college;
    private String iconpath;
    private String name;
    private String description;
    private Integer telephone;
    private String floor;
    private String dormitory;
    private Integer likecount;
    private Integer earnhow;
    private Integer publishcount;
    private Integer inquirycount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    public Integer getEarnhow() {
        return earnhow;
    }

    public void setEarnhow(Integer earnhow) {
        this.earnhow = earnhow;
    }

    public Integer getPublishcount() {
        return publishcount;
    }

    public void setPublishcount(Integer publishcount) {
        this.publishcount = publishcount;
    }

    public Integer getInquirycount() {
        return inquirycount;
    }

    public void setInquirycount(Integer inquirycount) {
        this.inquirycount = inquirycount;
    }

    @Override
    public String toString() {
        return "ShopExt [username=" + username + ", college=" + college + ", iconpath=" + iconpath + ", name=" + name
                + ", description=" + description + ", telephone=" + telephone + ", floor=" + floor + ", dormitory="
                + dormitory + ", likecount=" + likecount + ", earnhow=" + earnhow + ", publishcount=" + publishcount
                + ", inquirycount=" + inquirycount + "]";
    }


}


