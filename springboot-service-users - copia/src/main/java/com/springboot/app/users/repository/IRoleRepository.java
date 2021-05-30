package com.springboot.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.users.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
