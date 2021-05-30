package com.springboot.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.users.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}