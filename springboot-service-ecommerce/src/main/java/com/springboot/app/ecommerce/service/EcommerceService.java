package com.springboot.app.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.Sale;
import com.springboot.app.ecommerce.repository.ISaleRepository;

@Service
public class EcommerceService implements IEcommerceService {

	@Autowired
	private ISaleRepository repository;
	
	@Autowired
	private RestTemplate restClient;
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Sale> findAllByMonth(Integer month) {
		if(1 <= month && month <= 12) {
			return repository.findAllByMonth(month);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Sale> findAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Sale> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public Sale save(Sale sale) {
		return repository.save(sale);
	}
	
	
	@Override
	@Transactional
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	
}
