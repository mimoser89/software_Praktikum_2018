package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Investment;

import java.util.List;

@Repository("investmentRepository")
public interface InvestmentRepository extends JpaRepository<Investment, Integer>{
	List<Investment> findAll();
	List<Investment> findByuserId(int userId);
}
