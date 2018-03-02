package com.coolroof.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Michael Moser, Martin Schoenegger
 * A model class which represents the investment table
 * 
 **/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "investment")
public class Investment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "investment_id")
	private int id;
	@Column(name = "userId")
	private int userId;
	@Column(name = "roofIds")
	private String roofIds;
	@NotEmpty(message = "*Please select a message")
	private String space;
	@Column(name = "magnitude")
	private int magnitude;
	@Column(name = "investmentDate")
	private Timestamp investmentDate;

	private int[][] valuesOfInvestment;
	private double co2Saved;
	private double rtnOfInterests;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRoofIds() {
		return roofIds;
	}

	public void setRoofIds(String roofIds) {
		this.roofIds = roofIds;
	}

	public String getSpace() {
		return space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public int getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(int magnitude) {
		this.magnitude = magnitude;
	}

	public int[][] getValuesOfInvestment() {
		return valuesOfInvestment;
	}

	public void setValuesOfInvestment(int[][] valuesOfInvestment) {
		this.valuesOfInvestment = valuesOfInvestment;
	}

	public Timestamp getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(Timestamp investmentDate) {
		this.investmentDate = investmentDate;
	}
	
	public double getCo2Saved() {
		return co2Saved;
	}

	public void setCo2Saved(double co2Saved) {
		this.co2Saved = co2Saved;
	}

	public double getRtnOfInterests() {
		return rtnOfInterests;
	}

	public void setRtnOfInterests(double rtnOfInterests) {
		this.rtnOfInterests = rtnOfInterests;
	}
	
	
}