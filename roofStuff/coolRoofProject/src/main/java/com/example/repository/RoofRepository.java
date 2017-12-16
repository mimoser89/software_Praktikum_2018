package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Roof;

@Repository("roofRepository")
public interface RoofRepository extends JpaRepository<Roof, Long> {
	
	Roof findRoofByAddress(String address);
	
	List<Roof> findAllRoofsByUserId(int uId);
	
	List<Roof> findAll();
}
