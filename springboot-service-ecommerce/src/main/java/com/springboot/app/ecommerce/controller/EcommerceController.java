package com.springboot.app.ecommerce.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.ecommerce.service.EcommerceService;
import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Sale;

@RestController
public class EcommerceController {
	
	@Autowired
	private RestTemplate restClient;
	
	@Autowired
	private EcommerceService service;
	
	
	
	
	
	@GetMapping("/api/product/find/id/{id}")
	public ResponseEntity<Object> productFindById(@PathVariable Long id) {
		
		String uri = "http://localhost:8001/find/id/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
		
	    ResponseEntity<Object> response = restClient.getForEntity(
				uri,
				Object.class,
				params);
		
	    return response;
	}
	
	@GetMapping("/api/product/find/offers")
	public ResponseEntity<Object> productFindOffers(){
		
		String uri = "http://localhost:8001/find/offers";
		
		ResponseEntity<Object> response = restClient.getForEntity(
				uri,
				Object.class);
		
		return response;
	}
	
	@GetMapping("/api/product/find/all")
	public ResponseEntity<Object> productFindAll() {
		
		String uri = "http://localhost:8001/find/all";
		
		ResponseEntity<Object> response = restClient.getForEntity(
				uri,
				Object.class);
		
		return response;
	}
	
	@GetMapping("/api/product/hasEnoughStockToSell/{id}/{stockToSell}")
	public ResponseEntity<Object> productHasEnoughStockToSell(@PathVariable Integer stockToSell, @PathVariable Long id) {
		
		String uri = "http://localhost:8001/hasEnoughStockToSell/{id}/{stockToSell}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    params.put("stockToSell", stockToSell);
		
	    ResponseEntity<Object> response = restClient.getForEntity(
				uri,
				Object.class,
				params);
		
	    return response;
	}
	
	
	
	
	
	@PostMapping("/api/product/save")
	public ResponseEntity<Object> productSave(@RequestBody Product product) {

		String uri = "http://localhost:8001/save"; 
		
		ResponseEntity<Object> response = restClient.postForEntity(
				uri,
				product,
				Object.class);
		
		return response;
		
	}
	
	@PostMapping("/api/product/save/all")
	public ResponseEntity<Object> productSaveAll(@RequestBody List<Product> products) {

		String uri = "http://localhost:8001/save/all";
		
		ResponseEntity<Object> response = restClient.postForEntity(
				uri,
				products,
				Object.class);
		
		return response;
	}
	
	
	
	
	
	@PutMapping("/api/product/edit/price/{id}")
	public ResponseEntity<Object> productEditPrice(@RequestBody Double price, @PathVariable Long id){

		String uri = "http://localhost:8001/edit/price/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    
	    restClient.put(
	    		uri,
	    		price,
	    		params);
	    
	    return productFindById(id);
	}
	
	@PutMapping("/api/product/edit/price/map")
	public ResponseEntity<Object> productEditPriceMap(@RequestBody Map<Long, Double> products){

		String uri = "http://localhost:8001/edit/price/map";
		
		restClient.put(
				uri,
				products);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PutMapping("/api/product/edit/putOnSale/{id}")
	public ResponseEntity<Object> productPutOnSale(@PathVariable Long id){

		String uri = "http://localhost:8001/edit/putOnSale/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    
	    restClient.put(
	    		uri,
	    		id,
	    		params);
	    
	    return productFindById(id);
	    
	}
	
	@PutMapping("/api/product/edit/putOnSale/list")
	public ResponseEntity<Object> productPutOnSaleList(@RequestBody List<Long> ids){

		String uri = "http://localhost:8001/edit/putOnSale/list";
		
		restClient.put(
				uri,
				ids);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PutMapping("/api/product/edit/removeOnSale/{id}")
	public ResponseEntity<Object> productRemoveOnSale(@PathVariable Long id){
		
		String uri = "http://localhost:8001/edit/removeOnSale/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    
	    restClient.put(
	    		uri,
	    		id,
	    		params);
	    
	    return productFindById(id);
	    
	}
	
	@PutMapping("/api/product/edit/removeOnSale/list")
	public ResponseEntity<Object> productRemoveOnSaleList(@RequestBody List<Long> ids){
		
		String uri = "http://localhost:8001/edit/removeOnSale/list";
		
		restClient.put(
				uri,
				ids);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@PutMapping("/api/product/edit/stock/add/{id}")
	public ResponseEntity<Object> productAddStock(@RequestBody Integer stock, @PathVariable Long id){

		String uri = "http://localhost:8001/edit/stock/add/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    
	    restClient.put(
	    		uri,
	    		stock,
	    		params);
	    
	    return productFindById(id);
		
	}
	
	@PutMapping("/api/product/edit/stock/sell/{id}")
	public ResponseEntity<Object> productSellStock(@RequestBody Integer stock, @PathVariable Long id){

		String uri = "http://localhost:8001/edit/stock/sell/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    
	    restClient.put(
	    		uri,
	    		stock,
	    		params);
	    
	    return productFindById(id);
		
	}
	
	
	
	
	
	@DeleteMapping("/api/product/delete/all")
	public ResponseEntity<Object> productDeleteAll(){
		
		String uri = "http://localhost:8001/delete/all";
		
		if(productFindAll().getStatusCode() == HttpStatus.OK) {
			restClient.delete(uri);
			return new ResponseEntity<>("All products have been deleted.", HttpStatus.OK);
		}
		
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
		
	}
	
	@DeleteMapping("/api/product/delete/{id}")
	public ResponseEntity<Object> productDeleteById(@PathVariable Long id){
		
		String uri = "http://localhost:8001/delete/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    
	    if(productFindById(id).getStatusCode() == HttpStatus.OK) {
	    	restClient.delete(uri, params);
	    	return new ResponseEntity<>("Product of id " + id + " has been deleted.", HttpStatus.OK);
	    }
	    return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	    
	}
	
	
	
	
	
	@GetMapping("/api/cart/list")
	public ResponseEntity<Object> getCart(){
		List<CartItem> cart = service.getCart();
		
		
		if(cart == null || cart.isEmpty()) {
			return new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/add/{id}")
	public ResponseEntity<Object> addProdToCart(@PathVariable Long id, @RequestBody Integer amount){
		
		if(amount > 0) {
			ResponseEntity<Object> found = productFindById(id);
			
			if(found.getStatusCode() == HttpStatus.OK) {
				ResponseEntity<Object> enoughStock = productHasEnoughStockToSell(amount, id);
				if(enoughStock.getStatusCode() == HttpStatus.OK) {
					service.addProdToCart(id, amount);
					return new ResponseEntity<>("Added " + amount + " unit(s) of the product.", HttpStatus.CREATED);
				} else {
					return new ResponseEntity<>("Error: not enough stock.", HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>("Error: product not found.", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("Error: invalid amount.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/api/cart/remove/{id}")
	public ResponseEntity<Object> removeProdFromCart(@PathVariable Long id, @RequestBody Integer amount){
		
		if(!service.cartIsEmpty()) {
		
			if(amount > 0) {
				ResponseEntity<Object> found = productFindById(id);
				
				if(found.getStatusCode() == HttpStatus.OK) {
					
					if(service.prodIsInCart(id)) {
						service.removeProdFromCart(id, amount);
						return new ResponseEntity<>("Removed " + amount + " unit(s) of the product.", HttpStatus.CREATED);
					}
					return new ResponseEntity<>("Error, that product is not in the cart.", HttpStatus.BAD_REQUEST); 
				} else {
					return new ResponseEntity<>("Error: product not found", HttpStatus.BAD_REQUEST);
				} 
			} else {
				return new ResponseEntity<>("Error: invalid amount.", HttpStatus.BAD_REQUEST);
			}
		
		} else {
			return new ResponseEntity<>("Error: empty cart.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/api/cart/purchase")
	public ResponseEntity<Object> purchaseCart(){
		List<CartItem> cart = service.getCart();
		if(cart == null || cart.isEmpty()) {
			return new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.purchaseCart(), HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/clear")
	public ResponseEntity<Object> clearCart(){
		List<CartItem> cart = service.getCart();
		if(cart == null || cart.isEmpty()) {
			return new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		}
		service.clearCart();
		return new ResponseEntity<>("Shopping cart cleared.", HttpStatus.OK);
	}
	
	
	
	
	
	@GetMapping("/api/sale/find/all")
	public ResponseEntity<Object> findAll(){
		List<Sale> sales = service.findAll();
		if(sales.size() > 0) {
			return new ResponseEntity<Object>(sales, HttpStatus.OK);			
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/sale/find/month/{month}")
	public ResponseEntity<Object> findAllByMonth(@PathVariable Integer month){
		List<Sale> sales = service.findAllByMonth(month);
		if(sales != null) {
			return new ResponseEntity<Object>(sales, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Invalid month", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/sale/find/id/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<Sale> saleSearch = service.findById(id);
		if(saleSearch.isPresent()) {
			Sale saleInDB = saleSearch.get();
			return new ResponseEntity<>(saleInDB, HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no sale with such id.", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/api/sale/delete/all")
	public ResponseEntity<Object> deleteAll() {
		service.deleteAll();
		return new ResponseEntity<>("All sales have been deleted", HttpStatus.OK);
	}
	
	@DeleteMapping("/api/sale/delete/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Long id) {
		Optional<Sale> saleSearch = service.findById(id);
		if(saleSearch.isPresent()) {
			service.deleteById(id);
			return new ResponseEntity<>("Sale of id " + id + " has been deleted.", HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no sale with such id.", HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	@GetMapping("/api/cart/prodIsInCart/{id}")
	public ResponseEntity<Boolean> prodIsInCart(@PathVariable Long id){
		return new ResponseEntity<>(service.prodIsInCart(id), HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/removeIfnoAmountInCart/{id}")
	public ResponseEntity<Boolean> removeIfnoAmountInCart(@PathVariable Long id){
		Boolean res = service.cart.get(id) == 0;
		if(res) {
			service.cart.remove(id);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		return new ResponseEntity<>(false, HttpStatus.OK);
	}
}
