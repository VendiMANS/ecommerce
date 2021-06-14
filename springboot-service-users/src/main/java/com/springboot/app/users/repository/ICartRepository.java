package com.springboot.app.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.users.model.Cart;

public interface ICartRepository extends JpaRepository<Cart, Long> {

}
