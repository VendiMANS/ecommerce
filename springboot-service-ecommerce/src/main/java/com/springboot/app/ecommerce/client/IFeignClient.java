package com.springboot.app.ecommerce.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.app.ecommerce.model.Product;

@FeignClient(name="service-products", url="localhost:8001")
public interface IFeignClient {

	@GetMapping("/find/id/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id);
	
	@GetMapping("/find/name")
	public ResponseEntity<Object> findByName(@RequestBody String name);
	
	@GetMapping("/find/offers")
	public ResponseEntity<Object> findOffers();
	
	@GetMapping("/find/list")
	public ResponseEntity<Object> findList(@RequestBody List<Long> ids);
	
	@GetMapping("/find/all")
	public ResponseEntity<Object> findAll();
	

	
	@PostMapping("/save")
	public ResponseEntity<Object> save(@RequestBody Product product);
	
	@PostMapping("/save/all")
	public ResponseEntity<Object> saveAll(@RequestBody List<Product> products);
	
	
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<Object> edit(@RequestBody Product product, @PathVariable Long id);
	
	@PutMapping("/edit/price/{id}")
	public ResponseEntity<Object> editPrice(@RequestBody Double price, @PathVariable Long id);
	
	@PutMapping("/edit/price/map")
	public ResponseEntity<Object> editPriceMap(@RequestBody Map<Long, Double> IdAndPrices);
	
	@PutMapping("/edit/putOnSale/{id}")
	public ResponseEntity<Object> putOnSale(@PathVariable Long id);
	
	@PutMapping("/edit/putOnSale/list")
	public ResponseEntity<Object> putOnSaleList(@RequestBody List<Long> ids);
	
	@PutMapping("/edit/removeOnSale/{id}")
	public ResponseEntity<Object> removeOnSale(@PathVariable Long id);
	
	@PutMapping("/edit/removeOnSale/list")
	public ResponseEntity<Object> removeOnSaleList(@RequestBody List<Long> ids);
	
	@PutMapping("/stock/add/{id}")
	public ResponseEntity<Object> addStock(@RequestBody Integer stock, @PathVariable Long id);
	
	//@PutMapping("/stock/add/map")
	//public ResponseEntity<Object> addStockMap(@RequestBody Map<Long, Integer> idAndStocks);
	
	@PutMapping("/stock/sell/{id}")
	public ResponseEntity<Object> sellStock(@RequestBody Integer stock, @PathVariable Long id);
	
	
	
	@DeleteMapping("/delete/all")
	public ResponseEntity<Object> deleteAll();
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable Long id);
}
