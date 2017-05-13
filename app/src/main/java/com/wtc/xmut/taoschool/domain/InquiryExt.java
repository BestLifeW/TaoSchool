package com.wtc.xmut.taoschool.domain;

public class InquiryExt  {


    /**
     * id : 4
     * username : 123
     * password : null
     * name : 123
     * telephone : null
     * college : 软件学院
     * floor : null
     * dormitory : null
     * likecount : null
     * iconpath : /TaoSchool/img/434188d5-db5c-4615-99ee-5042a6cbea75.jpg
     * earnhow : null
     * publishcount : null
     * inquirycount : null
     * inquiryid : 0
     * ishopname : 21321
     * iprice : 321312
     * idescription : 321321
     * itime : 2017-05-10
     */

    private int id;
    private String username;
    private Object password;
    private String name;
    private String telephone;
    private String college;
    private Object floor;
    private Object dormitory;
    private Object likecount;
    private String iconpath;
    private Object earnhow;
    private Object publishcount;
    private Object inquirycount;
    private int inquiryid;
    private String ishopname;
    private String iprice;
    private String idescription;
    private String itime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Object getFloor() {
        return floor;
    }

    public void setFloor(Object floor) {
        this.floor = floor;
    }

    public Object getDormitory() {
        return dormitory;
    }

    public void setDormitory(Object dormitory) {
        this.dormitory = dormitory;
    }

    public Object getLikecount() {
        return likecount;
    }

    public void setLikecount(Object likecount) {
        this.likecount = likecount;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public Object getEarnhow() {
        return earnhow;
    }

    public void setEarnhow(Object earnhow) {
        this.earnhow = earnhow;
    }

    public Object getPublishcount() {
        return publishcount;
    }

    public void setPublishcount(Object publishcount) {
        this.publishcount = publishcount;
    }

    public Object getInquirycount() {
        return inquirycount;
    }

    public void setInquirycount(Object inquirycount) {
        this.inquirycount = inquirycount;
    }

    public int getInquiryid() {
        return inquiryid;
    }

    public void setInquiryid(int inquiryid) {
        this.inquiryid = inquiryid;
    }

    public String getIshopname() {
        return ishopname;
    }

    public void setIshopname(String ishopname) {
        this.ishopname = ishopname;
    }

    public String getIprice() {
        return iprice;
    }

    public void setIprice(String iprice) {
        this.iprice = iprice;
    }

    public String getIdescription() {
        return idescription;
    }

    public void setIdescription(String idescription) {
        this.idescription = idescription;
    }

    public String getItime() {
        return itime;
    }

    public void setItime(String itime) {
        this.itime = itime;
    }

    @Override
    public String toString() {
        return "InquiryExt{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", name='" + name + '\'' +
                ", telephone=" + telephone +
                ", college='" + college + '\'' +
                ", floor=" + floor +
                ", dormitory=" + dormitory +
                ", likecount=" + likecount +
                ", iconpath='" + iconpath + '\'' +
                ", earnhow=" + earnhow +
                ", publishcount=" + publishcount +
                ", inquirycount=" + inquirycount +
                ", inquiryid=" + inquiryid +
                ", ishopname='" + ishopname + '\'' +
                ", iprice='" + iprice + '\'' +
                ", idescription='" + idescription + '\'' +
                ", itime='" + itime + '\'' +
                '}';
    }
}
