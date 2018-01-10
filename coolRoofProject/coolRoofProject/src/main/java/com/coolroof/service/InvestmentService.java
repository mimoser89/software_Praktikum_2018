package com.coolroof.service;

import java.util.List;


import com.coolroof.model.Investment;

/**
 * @author Michael Moser, Martin Schoenegger
 * 
 **/

public interface InvestmentService {
	
	public List<Investment> findAll();
	public List<Investment> findByuserId(int userId);
	public void saveInvestment(Investment investment);
}
