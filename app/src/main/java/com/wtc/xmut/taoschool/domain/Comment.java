package com.wtc.xmut.taoschool.domain;

public class Comment {
    private Integer id;

    private String username;

    private Integer shopid;

    private String content;

    private String ousername;

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

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getOusername() {
        return ousername;
    }

    public void setOusername(String ousername) {
        this.ousername = ousername == null ? null : ousername.trim();
    }
}