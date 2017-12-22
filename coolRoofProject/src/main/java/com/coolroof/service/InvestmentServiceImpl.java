package com.coolroof.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coolroof.model.Investment;
import com.coolroof.repository.InvestmentRepository;

/**
 * @author Michael Moser, Martin Schoenegger
 * 
 **/

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


	@Override
	public void saveInvestment(Investment investment) {
		investmentRepository.save(investment);
		
	}

}
