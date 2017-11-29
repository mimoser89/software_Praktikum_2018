package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roofRepository")
public interface RoofRepository extends JpaRepository<Roof, Long> {
  public Roof findRoofByAddress(String address);
}