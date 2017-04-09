package com.wtc.xmut.taoschool.domain;

public class SubmitDetail{

	/**
	 * id : 1
	 * shopname : 43寸4K显示器 +GTX1080 映众1871频率+罗技G910 G502 传说装备
	 * description : null
	 * category : 1
	 * picture : /TaoSchool/img/0fbd63a7-13d5-40b4-b268-67572a5294e7.jpg
	 * price : 1200
	 * username : admin
	 * oldprice : null
	 * shoptime : null
	 * name : 要去个美丽的地方
	 * telephone : 123
	 * college : 理工学院
	 * floor : D栋
	 * dormitory : 128
	 */

	private int id;
	private String shopname;
	private Object description;
	private String category;
	private String picture;
	private String price;
	private String username;
	private Object oldprice;
	private Object shoptime;
	private String name;
	private String telephone;
	private String college;
	private String floor;
	private String dormitory;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public Object getDescription() {
		return description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Object getOldprice() {
		return oldprice;
	}

	public void setOldprice(Object oldprice) {
		this.oldprice = oldprice;
	}

	public Object getShoptime() {
		return shoptime;
	}

	public void setShoptime(Object shoptime) {
		this.shoptime = shoptime;
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
}
