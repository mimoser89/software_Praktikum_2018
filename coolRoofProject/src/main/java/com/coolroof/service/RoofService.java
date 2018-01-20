package com.coolroof.service;

import java.util.List;

import com.coolroof.model.Roof;

public interface RoofService {

	public List<Roof> findAllRoofsByUserId(int uId);

	public void saveRoof(Roof roof);
	
	public List<Roof> findAll();
	
}