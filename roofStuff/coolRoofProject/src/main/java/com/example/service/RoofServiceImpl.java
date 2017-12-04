package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Roof;
import com.example.repository.RoofRepository;

@Service("roofService")
public class RoofServiceImpl implements RoofService {

	@Autowired
	private RoofRepository roofRepository;

	@Override
	public Roof findRoofByAddress(String address) {
		return roofRepository.findRoofByAddress(address);
	}

	@Override
	public void saveRoof(Roof roof) {
		roof.setPrice(10);	// TODO calculate
		roof.setAreaLeft(roof.getArea());
		roofRepository.save(roof);
	}
	
	@Override
	public List<Roof> findAll() {
		return roofRepository.findAll();
	}
}