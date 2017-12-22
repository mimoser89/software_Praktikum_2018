package com.coolroof.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "roof")
public class Roof {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roof_id")
	private int roofId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "address")
	private String address;

	@NotBlank(message = "*Please provide a region")	
	@Column(name = "region")
	private String region;

	@Column(name = "angle")	// TODO unused
	private int angle;

	@Min(value = 0, message = "*Please select the age of your roof")
	@Column(name = "age")
	private int age;

	@NotBlank(message = "*Please select a roof material")
	@Column(name = "material")
	private String material;

	@Column(name = "area")
	private int area;
	
	@Column(name = "areaLeft")
	private int areaLeft;

	@Column(name = "price_per_sqm")
	private int price;
	
	@Column(name = "roofPolygon")
	private String roofPolygon;
	
	@Column(name = "longitude")
	private String longitude;
	
	@Column(name = "latitude")
	private String latitude;
	
	@Column(name = "zoomFactor")
	private String zoomFactor;
	
	public String getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(String zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

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
	
	public String getRoofPolygon() {
		return roofPolygon;
	}

	public void setRoofPolygon(String roofPolygon) {
		this.roofPolygon = roofPolygon;
	}
}