package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "roof")
public class Roof {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roof_id")
	private int roofId;

	@Column(name = "user_id")
	private int userId;

	@NotEmpty(message = "*Please provide an address")
	@Column(name = "address")
	private String address;

	@NotEmpty(message = "*Please provide a region")
	@Column(name = "region")
	private String region;

	@Column(name = "angle")
	private int angle;

	@Column(name = "age")
	private int age;

	@Column(name = "material")
	private String material;

	@Column(name = "area")
	private int area;

	@Column(name = "areaLeft")
	private int areaLeft;

	@Column(name = "price_per_sqm")
	private int price;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRoofId() {
		return roofId;
	}

	public void setRoofId(int roofId) {
		this.roofId = roofId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getAreaLeft() {
		return areaLeft;
	}

	public void setAreaLeft(int areaLeft) {
		this.areaLeft = areaLeft;
	}
}