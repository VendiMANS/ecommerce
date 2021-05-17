package com.springboot.app.users.service;

import com.springboot.app.users.model.User;

public interface IUserService {

    User findByUsername(String username);
    
    User save(User user);
}