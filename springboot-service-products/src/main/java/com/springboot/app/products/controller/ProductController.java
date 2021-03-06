package com.springboot.app.products.controller;

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
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		Optional<Product> productSearch = service.findById(id);
		if(productSearch.isPresent()) {
			return new ResponseEntity<>(productSearch.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/find/offers")
	public ResponseEntity<Object> findOffers(){
		List<Product> offers = service.findOffers();
		if(offers.size() > 0) {
			return new ResponseEntity<>(offers, HttpStatus.OK);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/find/list")
	public ResponseEntity<Object> findList(@RequestBody List<Long> ids) {
		List<Product> prodList = service.findList(ids);
		if(prodList.size() > 0) {
			return new ResponseEntity<>(prodList, HttpStatus.OK);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/find/all")
	public ResponseEntity<Object> findAll() {
		List<Product> allProds = service.findAll();
		if(allProds.size() > 0) {
			return new ResponseEntity<>(allProds, HttpStatus.OK);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<Object> save(@RequestBody Product product) {
		Optional<Product> productSearch = service.findByName(product.getName());
		if(!productSearch.isPresent()) {
			return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's already a product with such name.", HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<Object> editPrice(@RequestBody Double price, @PathVariable Long id) {
		Product productInDB = service.editPrice(price,id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST); 
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
	public ResponseEntity<Object> putOnSale(@PathVariable Long id) {
		Product productInDB = service.putOnSale(id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST); 
	}
	
	@PutMapping("/edit/putOnSale/list")
	public ResponseEntity<Object> putOnSaleList(@RequestBody List<Long> ids) {
		List<Product> onSaleList = service.putOnSaleList(ids);
		if(onSaleList.size() > 0) {
			return new ResponseEntity<>(onSaleList, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/edit/removeOnSale/{id}")
	public ResponseEntity<Object> removeOnSale(@PathVariable Long id) {
		Product productInDB = service.removeOnSale(id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST); 
	}
	
	@PutMapping("/edit/removeOnSale/list")
	public ResponseEntity<Object> removeOnSaleList(@RequestBody List<Long> ids) {
		List<Product> notOnSaleList = service.removeOnSaleList(ids);
		if(notOnSaleList.size() > 0) {
			return new ResponseEntity<>(notOnSaleList, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/edit/stock/add/{id}")
	public ResponseEntity<Object> addStock(@RequestBody Integer stock, @PathVariable Long id) {
		Product productInDB = service.addStock(stock,id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST); 
	}
	/*
	@PutMapping("/stock/add/map")
	public ResponseEntity<Object> addStockMap(@RequestBody Map<Long, Integer> idAndStocks) {
		List<Product> editedProducts = service.addStockMap(idAndStocks);
		if(editedProducts.size() > 0) {
			return new ResponseEntity<>(editedProducts, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST); 
	}
	*/
	@PutMapping("/edit/stock/sell/{id}")
	public ResponseEntity<Object> sellStock(@RequestBody Integer stock, @PathVariable Long id) {
		Product productInDB = service.sellStock(stock,id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Not enough stock or invalid id.", HttpStatus.BAD_REQUEST); 
	}
	
	/*@PutMapping("/sell/{id}")
	public ResponseEntity<Object> sell(@RequestBody Integer stockToSell, @PathVariable Long id) {
		Sale sale = service.sell(stockToSell, id);
		if(sale!= null) {
			return new ResponseEntity<>(sale, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id or not enough stock.", HttpStatus.BAD_REQUEST); 
	}
	
	@PutMapping("/sell/map")
	public ResponseEntity<Object> sellMap(@RequestBody Map<Long, Integer> idAndStocks) {
		List<Sale> sales = service.sellMap(idAndStocks);
		if(sales.size() > 0) {
			return new ResponseEntity<>(sales, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids or none have enough stock.", HttpStatus.BAD_REQUEST);
	}*/

	
	
	
	
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
