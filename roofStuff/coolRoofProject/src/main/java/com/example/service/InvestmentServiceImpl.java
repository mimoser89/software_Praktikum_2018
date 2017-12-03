package com.example.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Investment;
import com.example.repository.InvestmentRepository;

@Service("investmentService")
public class InvestmentServiceImpl implements InvestmentService{

	@Autowired
	private InvestmentRepository investmentRepository;
	
	
	public List<Investment> findAll() {
		return investmentRepository.findAll();
	}


	@Override
	public List<Investment> findByuserId(int userId) {
		return investmentRepository.findByuserId(userId);
	}

}
