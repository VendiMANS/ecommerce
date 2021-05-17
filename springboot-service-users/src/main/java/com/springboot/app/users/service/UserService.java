package com.springboot.app.users.service;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.app.users.model.User;
import com.springboot.app.users.repository.IRoleRepository;
import com.springboot.app.users.repository.IUserRepository;

@Service
public class UserService implements IUserService {

	@Autowired
    private IUserRepository userRepository;
	
    @Autowired
    private IRoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User save(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
        return user;
	}

}
