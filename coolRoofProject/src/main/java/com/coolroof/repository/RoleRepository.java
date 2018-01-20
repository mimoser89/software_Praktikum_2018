package com.coolroof.repository;

/**
 * @author Michael Moser, Martin Schoenegger
 * An interface for getting Roles from the database
 * 
 **/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolroof.model.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Role findByRole(String role);
}
