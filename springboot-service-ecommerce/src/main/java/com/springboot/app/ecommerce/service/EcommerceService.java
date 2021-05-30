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
	
	
	
	public Map<Long, Integer> cart = new HashMap<>();
	
	
	
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
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	
	
	@Override
	@Transactional(readOnly = true)
	public List<CartItem> getCart(){
		List<CartItem> res = new ArrayList<>();
		
		Set<Long> ids = cart.keySet();
		
		String uri = "http://localhost:8001/getProduct/{id}";
		
		for(Long id : ids) {
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", id.toString());
			
			ResponseEntity<Product> productSearch = restClient.getForEntity(
					uri,
					Product.class,
					params);
			
			if(productSearch.getStatusCode() == HttpStatus.OK) {
				
				Product prod = (Product) productSearch.getBody();
				Integer amount = cart.get(id);
				CartItem currentItem = new CartItem(prod,amount);
				res.add(currentItem);
				
			} else {
				
				cart.remove(id);
				
			}
		}
		
		if(res.size() > 0) {
			return res;
		}
		return null;
	}
	
	@Override
	@Transactional
	public void addProdToCart(Long id, Integer amount){
		if(amount > 0) {
			
			String uri = "http://localhost:8001/find/id/{id}";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			
			ResponseEntity<Object> productSearch = restClient.getForEntity(
					uri,
					Object.class,
					params);
			
			if(productSearch.getStatusCode() == HttpStatus.OK) {
				
				uri = "http://localhost:8001/hasEnoughStockToSell/{id}/{stockToSell}";
				
				params.put("stockToSell", amount);
				
				ResponseEntity<Boolean> productHasEnoughStock = restClient.getForEntity(
						uri,
						Boolean.class,
						params);
				
				if(productHasEnoughStock.getBody()) {
					
					uri = "http://localhost:8001/edit/stock/sell/{id}";
					
					params.clear();
				    params.put("id", id);
				    
				    restClient.put(
				    		uri,
				    		amount,
				    		params);
					
					if(cart.containsKey(id)) {
						Integer currentAmount = cart.get(id);
						cart.put(id, currentAmount + amount);
					} else {
						cart.put(id, amount);
					}
					
				}
				
			} else {
				
				cart.remove(id);
				
			}
		}
	}
	
	@Override
	@Transactional
	public void removeProdFromCart(Long id, Integer amount){
		if(amount > 0) {
			
			String uri = "http://localhost:8001/find/id/{id}";
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			
			ResponseEntity<Object> productSearch = restClient.getForEntity(
					uri,
					Object.class,
					params);
			
			if(productSearch.getStatusCode() == HttpStatus.OK) {
				
				uri = "http://localhost:8001/edit/stock/add/{id}";
			    
			    restClient.put(
			    		uri,
			    		amount,
			    		params);
			    
				Integer currentAmount = cart.get(id);
				/*
				if(currentAmount - amount <= 0) {
					cart.remove(id);
				} else {
					cart.put(id, currentAmount - amount);
				}
				*/
				
				cart.put(id, currentAmount - amount);
				
			} else {
				
				cart.remove(id);
				
			}
		}
	}
	
	@Override
	@Transactional
	public List<CartItem> purchaseCart() {
		List<CartItem> purchase = new ArrayList<>();
		
		Set<Long> ids = cart.keySet();
		for(Long id : ids) {
			
			String uri = "http://localhost:8001/getProduct/{id}";
			
			Map<String, String> params = new HashMap<String, String>();
		    params.put("id", id.toString());
			
		    ResponseEntity<Product> response = restClient.getForEntity(
					uri,
					Product.class,
					params);
			
			if(response.getStatusCode() == HttpStatus.OK) {
				Product prodInDB = response.getBody();
				Integer amount = cart.get(id);
				
				Sale newSale = new Sale(prodInDB.getName(), amount);
				repository.save(newSale);
				
				CartItem newItem = new CartItem(prodInDB,amount);
				purchase.add(newItem);
				
			}
		}
		cart.clear();
		
		return purchase;
	}
	
	@Override
	@Transactional
	public void clearCart() {
		Set<Long> ids = cart.keySet();
		for(Long id : ids) {				// TODO OJO CON ESTO!!!!
			Integer amount = cart.get(id);
			removeProdFromCart(id, amount);	// si remuevo del cart no sabe avanzar al proximo id!!!
		}
		cart.clear();
	}
	
	
	
	
	@Override
	@Transactional
	public Boolean cartIsEmpty() {
		return cart.size() == 0;
	}
	
	@Override
	@Transactional
	public Boolean prodIsInCart(Long id) {
		return cart.containsKey(id);
	}
	
	
}
