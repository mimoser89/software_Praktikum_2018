package com.coolroof.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolroof.model.Roof;

@Repository("roofRepository")
public interface RoofRepository extends JpaRepository<Roof, Long> {
	
	List<Roof> findAllRoofsByUserId(int uId);
	
	List<Roof> findAll();
}
