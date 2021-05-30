package com.springboot.app.products.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.springboot.app.products.model.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT * FROM products WHERE name = ?1",
			nativeQuery = true)
	public Optional<Product> findByName(String name);
	
	@Query(value = "SELECT * FROM products WHERE id IN ?1",
			nativeQuery = true)
	public List<Product> findList(List<Long> ids);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "ALTER TABLE products AUTO_INCREMENT = 1",
			nativeQuery = true)
	public void resetIdCounter();
}
