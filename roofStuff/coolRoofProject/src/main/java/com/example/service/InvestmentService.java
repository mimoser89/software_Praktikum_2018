package com.example.service;

import com.example.model.Investment;
import java.util.List;

public interface InvestmentService {
	public List<Investment> findAll();
	public List<Investment> findByuserId(int userId);
}
