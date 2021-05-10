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
	
	public Product productSave(Product product);
	public List<Product> productSaveAll(List<Product> products);
	public Product productEditPrice(Double price, Long id);
	public List<Product> productEditPriceMap( Map<Long, Double> products);
	public Product productPutOnSale(Long id);
	public List<Product> productPutOnSaleList(List<Long> ids);
	public Product productRemoveOnSale(Long id);
	public List<Product> productRemoveOnSaleList(List<Long> ids);
	public Product productAddStock(Integer stock, Long id);
	//public List<Product> productAddStockMap(Map<Long, Integer> idAndStocks);
	public Product productFindById(Long id);
	public Product productFindByName(String name);
	public List<Product> productFindAll();
	public List<Product> productFindOffers();
	public void productDeleteById(Long id);
	public void productDeleteAll();
	
	
	
	public List<Sale> findAllByMonth(Integer month);
	public List<Sale> findAll();
	public Optional<Sale> findById(Long id);
	
	public void deleteAll();
	public void deleteById(Long id);
	
	public List<CartItem> getCart();
	public Product addProdToCart(Long prodId, Integer amount);
	public Product removeProdFromCart(Long prodId, Integer amount);
	public List<CartItem> purchaseCart();
	public void clearCart();
}
