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

import com.springboot.app.ecommerce.client.IFeignClient;
import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.Sale;
import com.springboot.app.ecommerce.repository.ISaleRepository;

@Service
public class EcommerceService implements IEcommerceService {

	@Autowired
	private ISaleRepository repository;
	
	@Autowired
	private IFeignClient client;
	
	private Map<Long, Integer> cart = new HashMap<>();
	
	
	
	
	
	@Override
	@Transactional
	public Product productSave(Product product) {
		ResponseEntity<Object> response = client.save(product);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (Product) response.getBody();
	}
	
	@Override
	@Transactional
	public List<Product> productSaveAll(List<Product> products) {
		ResponseEntity<Object> response = client.saveAll(products);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
	
	@Override
	@Transactional
	public Product productEditPrice(Double price, Long id){
		ResponseEntity<Object> response = client.editPrice(price, id);
		if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			return null;
		}
		return (Product) response.getBody();
	}
	
	@Override
	@Transactional
	public List<Product> productEditPriceMap(Map<Long, Double> products){
		ResponseEntity<Object> response = client.editPriceMap(products);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
	
	@Override
	@Transactional
	public Product productPutOnSale(Long id){
		ResponseEntity<Object> response = client.putOnSale(id);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (Product) response.getBody();
	}
	
	@Override
	@Transactional
	public List<Product> productPutOnSaleList(List<Long> ids){
		ResponseEntity<Object> response = client.putOnSaleList(ids);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
	
	@Override
	@Transactional
	public Product productRemoveOnSale(Long id){
		ResponseEntity<Object> response = client.removeOnSale(id);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (Product) response.getBody();
	}
	
	@Override
	@Transactional
	public List<Product> productRemoveOnSaleList(List<Long> ids){
		ResponseEntity<Object> response = client.removeOnSaleList(ids);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
	
	@Override
	@Transactional
	public Product productAddStock(Integer stock, Long id){
		ResponseEntity<Object> response = client.addStock(stock, id);
		if(response.getStatusCode() != HttpStatus.CREATED) {
			return null;
		}
		return (Product) response.getBody();
	}
	/*
	@Override
	@Transactional
	public List<Product> productAddStockMap(Map<Long, Integer> idAndStocks) {
		ResponseEntity<Object> response = client.addStockMap(idAndStocks);
		if(response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
*/
	@Override
	@Transactional(readOnly = true)
	public Product productFindById(Long id) {
		ResponseEntity<Object> response = client.findById(id);
		if(response.getStatusCode() != HttpStatus.OK) {
			return null;
		}
		return (Product) response.getBody();
	}

	@Override
	@Transactional(readOnly = true)
	public Product productFindByName(String name) {
		ResponseEntity<Object> response = client.findByName(name);
		if(response.getStatusCode() != HttpStatus.OK) {
			return null;
		}
		return (Product) response.getBody();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> productFindOffers(){
		ResponseEntity<Object> response = client.findOffers();
		if(response.getStatusCode() != HttpStatus.OK) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> productFindAll() {
		ResponseEntity<Object> response = client.findAll();
		if(response.getStatusCode() != HttpStatus.OK) {
			return null;
		}
		return (List<Product>) response.getBody();
	}
	
	@Override
	@Transactional
	public void productDeleteById(Long id) {
		ResponseEntity<Object> productSearch = client.findById(id);
		if(productSearch.getStatusCode() == HttpStatus.OK) {
			cart.remove(id);
			client.deleteById(id);
		}
	}
	
	@Override
	@Transactional
	public void productDeleteAll() {
		client.deleteAll();
		cart.clear();
	}
	
	
	
	
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
		
		for(Long id : ids) {
			ResponseEntity<Object> productSearch = client.findById(id);
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
	public Product addProdToCart(Long prodId, Integer amount){
		if(amount > 0) {
			ResponseEntity<Object> productSearch = client.findById(prodId);
			if(productSearch.getStatusCode() == HttpStatus.OK) {
				
				Product productInDB = (Product) productSearch.getBody();
				if(productInDB.hasEnoughStockToSell(amount)) {
					client.sellStock(amount, prodId);
					if(cart.containsKey(prodId)) {
						Integer currentAmount = cart.get(prodId);
						cart.put(prodId, currentAmount + amount);
					} else {
						cart.put(prodId, amount);
					}
					return productInDB;
				}
				
			} else {
				
				cart.remove(prodId);
				return null;
				
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public Product removeProdFromCart(Long prodId, Integer amount){
		if(amount > 0) {
			ResponseEntity<Object> productSearch = client.findById(prodId);
			if(productSearch.getStatusCode() == HttpStatus.OK) {
				
				Product productInDB = (Product) productSearch.getBody();
				client.addStock(amount, prodId);
				Integer currentAmount = cart.get(prodId);
				if(currentAmount - amount <= 0) {
					cart.remove(prodId);
				} else {
					cart.put(prodId, currentAmount - amount);
				}
				return productInDB;
				
			} else {
				
				cart.remove(prodId);
				return null;
				
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<CartItem> purchaseCart() {
		List<CartItem> purchase = new ArrayList<>();
		
		Set<Long> ids = cart.keySet();
		for(Long id : ids) {
			ResponseEntity<Object> prodSearch =  client.findById(id);
			if(prodSearch.getStatusCode() == HttpStatus.OK) {
				Product prodInDB = (Product) prodSearch.getBody();
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
		for(Long id : ids) {
			Integer amount = cart.get(id);
			removeProdFromCart(id, amount);
		}
	}
	
	
	
}
