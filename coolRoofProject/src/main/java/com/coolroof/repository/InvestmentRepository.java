package com.coolroof.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolroof.model.Investment;

import java.util.List;

/**
 * @author Michael Moser, Martin Schoenegger
 * An interface for getting investments from the database
 * 
 **/

@Repository("investmentRepository")
public interface InvestmentRepository extends JpaRepository<Investment, Integer>{
	
	List<Investment> findAll();
	List<Investment> findByuserId(int userId);
}
