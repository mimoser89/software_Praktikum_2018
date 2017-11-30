package com.example.service;

import com.example.model.Roof;

public interface RoofService {
	public Roof findRoofByAddress(String address);

	public void saveRoof(Roof roof);
}