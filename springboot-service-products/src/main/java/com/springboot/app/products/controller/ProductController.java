package com.springboot.app.products.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.products.model.Product;
import com.springboot.app.products.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	

	@GetMapping("/find/id/{id}")
	public Product findById(@PathVariable Long id) {
		Optional<Product> productSearch = service.findById(id);
		if(productSearch.isPresent()) {
			return productSearch.get();
		}
		return null;
	}
	
	@GetMapping("/find/offers")
	public List<Product> findOffers(){
		return service.findOffers();
	}
	
	@GetMapping("/find/list")
	public List<Product> findList(@RequestBody List<Long> ids) {
		return service.findList(ids);
	}
	
	@GetMapping("/find/all")
	public List<Product> findAll() {
		return service.findAll();
	}
	
	
	
	@GetMapping("/hasEnoughStockToSell/{id}/{stockToSell}")
	public ResponseEntity<Boolean> hasEnoughStockToSell(@PathVariable Integer stockToSell, @PathVariable Long id) {
		Optional<Product> productSearch = service.findById(id);
		if(productSearch.isPresent()) {
			Product prod = productSearch.get();
			return new ResponseEntity<>(prod.hasEnoughStockToSell(stockToSell), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getProduct/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id){
		Optional<Product> productSearch = service.findById(id);
		if(productSearch.isPresent()) {
			Product prod = productSearch.get();
			return new ResponseEntity<>(prod, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	@PostMapping("/save")
	public Product save(@RequestBody Product product) {
		return service.save(product);
	}
	
	@PostMapping("/save/all")
	public ResponseEntity<Object> saveAll(@RequestBody List<Product> products) {
		List<Product> saves = service.saveAll(products);
		if(saves.size() > 0) {
			return new ResponseEntity<>(saves, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<Object> edit(@RequestBody Product product, @PathVariable Long id) {
		Product productInDB = service.edit(product,id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST); 
	}
	
	@PutMapping("/edit/price/{id}")
	public Product editPrice(@RequestBody Double price, @PathVariable Long id) {
		return service.editPrice(price,id);
	}
	
	@PutMapping("/edit/price/map")
	public ResponseEntity<Object> editPriceMap(@RequestBody Map<Long, Double> IdAndPrices) {
		List<Product> editedProducts = service.editPriceMap(IdAndPrices);
		if(editedProducts.size() > 0) {
			return new ResponseEntity<>(service.editPriceMap(IdAndPrices), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST); 
	}
	
	@PutMapping("/edit/putOnSale/{id}")
	public Product putOnSale(@PathVariable Long id) {
		return service.putOnSale(id); 
	}
	
	@PutMapping("/edit/removeOnSale/{id}")
	public Product removeOnSale(@PathVariable Long id) {
		return service.removeOnSale(id); 
	}
	
	@PutMapping("/edit/stock/add/{id}")
	public Product addStock(@RequestBody Integer stock, @PathVariable Long id) {
		return service.addStock(stock,id); 
	}
	
	@PutMapping("/edit/stock/sell/{id}")
	public Product sellStock(@RequestBody Integer stock, @PathVariable Long id) {
		return service.sellStock(stock,id); 
	}
	
	
	
	
	
	@DeleteMapping("/delete/all")
	public ResponseEntity<Object> deleteAll() {
		service.deleteAll();
		return new ResponseEntity<>("All products have been deleted.", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Long id) {
		Optional<Product> productSearch = service.findById(id);
		if(productSearch.isPresent()) {
			service.deleteById(id);
			return new ResponseEntity<>("Product of id " + id + " has been deleted.", HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	
	
}
