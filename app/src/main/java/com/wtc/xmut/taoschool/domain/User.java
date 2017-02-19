package com.wtc.xmut.taoschool.domain;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String name;

    private Integer telephone;

    private String college;

    private String floor;

    private String dormitory;

    private Integer likecount;

    private String iconpath;

    private Integer earnhow;

    public Integer getEarnhow() {
        return earnhow;
    }

    public void setEarnhow(Integer earnhow) {
        this.earnhow = earnhow;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory == null ? null : dormitory.trim();
    }

    public Integer getLikecount() {
        return likecount;
    }

    public void setLikecount(Integer likecount) {
        this.likecount = likecount;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath == null ? null : iconpath.trim();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", telephone=" + telephone +
                ", college='" + college + '\'' +
                ", floor='" + floor + '\'' +
                ", dormitory='" + dormitory + '\'' +
                ", likecount=" + likecount +
                ", iconpath='" + iconpath + '\'' +
                ", earnhow=" + earnhow +
                '}';
    }
}