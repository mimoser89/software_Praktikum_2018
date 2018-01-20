package com.coolroof.repository;

/**
 * @author Michael Moser, Martin Schoenegger
 * An interface for getting Users from the database
 * 
 **/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coolroof.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}
