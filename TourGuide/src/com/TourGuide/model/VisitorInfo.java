package com.TourGuide.model;

/**
 * 游客信息
 * @author Tian
 *
 */
public class VisitorInfo {

	private String phone; //游客手机号
	private String name; //游客姓名
	private String nickName; //昵称
	private String image; // 头像
	private String sex; //性别
	private String city; //所在城市
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
