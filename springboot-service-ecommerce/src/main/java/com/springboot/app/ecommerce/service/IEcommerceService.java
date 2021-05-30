package com.springboot.app.ecommerce.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Sale;

public interface IEcommerceService {
	
	
	
	public List<Sale> findAllByMonth(Integer month);
	public List<Sale> findAll();
	public Optional<Sale> findById(Long id);
	
	public void deleteAll();
	public void deleteById(Long id);
	
	public List<CartItem> getCart();
	public void addProdToCart(Long prodId, Integer amount);
	public void removeProdFromCart(Long prodId, Integer amount);
	public List<CartItem> purchaseCart();
	public void clearCart();
	
	public Boolean cartIsEmpty();
	
	public Boolean prodIsInCart(Long id);
}
