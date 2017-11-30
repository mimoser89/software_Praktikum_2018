package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roof")
public class Roof {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roof_id")
	private int roof_id;

//	 @Column(name="user_id")
//	 private int user_id;
//	
	@Column(name = "address")
	private String address;
	
//	@Column(name = "region")
//	private String region;
//
//	@Column(name = "angle")
//	private int angle;
//
//	@Column(name = "age")
//	private int age;
//
//	@Column(name = "material")
//	private String material;
//
//	@Column(name = "area")
//	private int area;

	public int getRoof_id() {
		return roof_id;
	}

	public void setRoof_id(int roof_id) {
		this.roof_id = roof_id;
	}

//	public int getUser_id() {
//		return user_id;
//	}
//
//	public void setUser_id(int user_id) {
//		this.user_id = user_id;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
//	public String getRegion() {
//		return region;
//	}
//	
//	public void setRegion(String region) {
//		this.region = region;
//	}
//
//	public int getAngle() {
//		return angle;
//	}
//
//	public void setAngle(int angle) {
//		this.angle = angle;
//	}
//
//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
//	public String getMaterial() {
//		return material;
//	}
//
//	public void setMaterial(String material) {
//		this.material = material;
//	}
//
//	public int getArea() {
//		return area;
//	}
//
//	public void setArea(int area) {
//		this.area = area;
//	}

}