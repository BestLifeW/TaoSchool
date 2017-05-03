package com.wtc.xmut.taoschool.domain;

public class OrdersExt {
    /* select shop.id as shopid,shop.price,
        user.iconpath,
        user.telephone,
        user.college,
        user.dormitory,
        user.floor
        ,shop.shopname
        ,shop.state as shopstate
        ,shop.username,
        orders.id as orderid,
        orders.buyerusername,
        orders.sellerusername,
        orders.time,
        orders.state as ordersstate,
        shop.picture
 from shop,orders,user
 where shop.id = orders.shopid and shop.username = user.username
*/	private int shopid;
    private String price;
    private String shopstate;
    private String username;
    private int orderid;
    private String shopname;
    private String buyerusername;
    private String sellerusername;
    private String ordersstate;
    private String iconpath;
    private String picture;
    private String time;
    private String college;
    private String dormitory;
    private String floor;
    private String telephone;
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getIconpath() {
        return iconpath;
    }
    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public int getShopid() {
        return shopid;
    }
    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getShopstate() {
        return shopstate;
    }
    public void setShopstate(String shopstate) {
        this.shopstate = shopstate;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getOrderid() {
        return orderid;
    }
    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
    public String getBuyerusername() {
        return buyerusername;
    }
    public void setBuyerusername(String buyerusername) {
        this.buyerusername = buyerusername;
    }
    public String getSellerusername() {
        return sellerusername;
    }
    public void setSellerusername(String sellerusername) {
        this.sellerusername = sellerusername;
    }
    public String getOrdersstate() {
        return ordersstate;
    }
    public void setOrdersstate(String ordersstate) {
        this.ordersstate = ordersstate;
    }
    public String getShopname() {
        return shopname;
    }
    public void setShopname(String shopname) {
        this.shopname = shopname;
    }
    public String getCollege() {
        return college;
    }
    public void setCollege(String college) {
        this.college = college;
    }
    public String getDormitory() {
        return dormitory;
    }
    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }
    public String getFloor() {
        return floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
