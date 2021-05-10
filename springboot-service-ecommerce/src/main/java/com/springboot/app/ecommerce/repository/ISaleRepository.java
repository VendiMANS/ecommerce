package com.springboot.app.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.app.ecommerce.model.Sale;

public interface ISaleRepository extends JpaRepository<Sale, Long>{

	@Query(value = "SELECT * FROM sales WHERE MONTH(date) = ?1",
			nativeQuery = true)
	public List<Sale> findAllByMonth(Integer month);
}
