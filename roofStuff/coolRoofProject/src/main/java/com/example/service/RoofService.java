package com.example.service;

import java.util.List;

import com.example.model.Roof;

public interface RoofService {
	public Roof findRoofByAddress(String address);
	
	public List<Roof> findAllRoofsByUserId(int uId);

	public void saveRoof(Roof roof);
	
	public List<Roof> findAll();
	
}