package com.coolroof.service;

import com.coolroof.model.User;

/**
 * @author Michael Moser, Martin Schoenegger
 * 
 **/

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
