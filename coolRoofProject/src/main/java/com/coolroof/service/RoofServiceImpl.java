package com.coolroof.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolroof.model.Roof;
import com.coolroof.repository.RoofRepository;

@Service("roofService")
public class RoofServiceImpl implements RoofService {

	@Autowired
	private RoofRepository roofRepository;

	@Override
	public List<Roof> findAllRoofsByUserId(int uId) {
		return roofRepository.findAllRoofsByUserId(uId);
	}

	@Override
	public void saveRoof(Roof roof) {
		roofRepository.save(roof);
	}
	
	@Override
	public List<Roof> findAll() {
		return roofRepository.findAll();
	}
}