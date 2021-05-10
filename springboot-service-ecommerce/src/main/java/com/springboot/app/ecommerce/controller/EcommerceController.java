package com.springboot.app.ecommerce.controller;

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

import com.springboot.app.ecommerce.service.EcommerceService;
import com.springboot.app.ecommerce.client.IFeignClient;
import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Sale;

@RestController
public class EcommerceController {
	
	@Autowired
	private EcommerceService service;
	
	@PostMapping("/api/product/save")
	public ResponseEntity<Object> productSave(@RequestBody Product product) {
		Product savedProd = service.productSave(product);
		if(savedProd != null) {
			return new ResponseEntity<>(savedProd, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's already a product with such name.", HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/api/product/save/list")
	public ResponseEntity<Object> productSaveAll(@RequestBody List<Product> products) {
		List<Product> savedProds = service.productSaveAll(products);
		if(savedProds != null) {
			return new ResponseEntity<>(service.productSaveAll(products), HttpStatus.CREATED);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/price/{id}")
	public ResponseEntity<Object> productEditPrice(@RequestBody Double price, @PathVariable Long id){
		Product productInDB = service.productEditPrice(price,id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/price/map")
	public ResponseEntity<Object> productEditPriceMap(@RequestBody Map<Long, Double> products){
		List<Product> editedProducts = service.productEditPriceMap(products);
		if(editedProducts != null) {
			return new ResponseEntity<>(editedProducts, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/putOnSale/{id}")
	public ResponseEntity<Object> productPutOnSale(@PathVariable Long id){
		Product productInDB = service.productPutOnSale(id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/putOnSale/list")
	public ResponseEntity<Object> productPutOnSaleList(@RequestBody List<Long> ids){
		List<Product> onSaleList = service.productPutOnSaleList(ids);
		if(onSaleList != null) {
			return new ResponseEntity<>(onSaleList, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/removeOnSale/{id}")
	public ResponseEntity<Object> productRemoveOnSale(@PathVariable Long id){
		Product productInDB = service.productRemoveOnSale(id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/removeOnSale/list")
	public ResponseEntity<Object> productRemoveOnSaleList(@RequestBody List<Long> ids){
		List<Product> notOnSaleList = service.productRemoveOnSaleList(ids);
		if(notOnSaleList != null) {
			return new ResponseEntity<>(notOnSaleList, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/api/product/edit/stock/add/{id}")
	public ResponseEntity<Object> productAddStock(@RequestBody Integer stock, @PathVariable Long id){
		Product productInDB = service.productAddStock(stock,id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	/*
	@PutMapping("/api/product/stock/add/map")
	public ResponseEntity<Object> productAddStockMap(@RequestBody Map<Long, Integer> idAndStocks){
		List<Product> editedProducts = service.productAddStockMap(idAndStocks);
		if(editedProducts.size() > 0) {
			return new ResponseEntity<>(editedProducts, HttpStatus.CREATED);
		}
		return new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
	}
*/
	@GetMapping("/api/product/find/id/{id}")
	public ResponseEntity<Object> productFindById(@PathVariable Long id) {
		Product productInDB = service.productFindById(id);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/product/find/name")
	public ResponseEntity<Object> productFindByName(@RequestBody String name) {
		Product productInDB = service.productFindByName(name);
		if(productInDB != null) {
			return new ResponseEntity<>(productInDB, HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no product with such name.", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/product/find/offers")
	public ResponseEntity<Object> productFindOffers(){
		List<Product> offers = service.productFindOffers();
		if(offers != null) {
			return new ResponseEntity<>(offers, HttpStatus.OK);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/api/product/find/all")
	public ResponseEntity<Object> productFindAll() {
		List<Product> allProds = service.productFindAll();
		if(allProds != null) {
			return new ResponseEntity<>(allProds, HttpStatus.OK);
		}
		return new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	@GetMapping("/api/cart/list")
	public ResponseEntity<Object> getCart(){
		List<CartItem> cart = service.getCart();
		if(cart.isEmpty()) {
			return new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/add/{id}")
	public ResponseEntity<Object> addProdToCart(@PathVariable Long productId, @RequestBody Integer amount){
		Product addedProd = service.addProdToCart(productId, amount);
		if(addedProd == null) {
			return new ResponseEntity<>("Error: invalid amount or product not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Added " + amount + " unit(s) of the product " + addedProd.getName(), HttpStatus.CREATED); 
	}
	
	@PutMapping("/api/cart/remove/{id}")
	public ResponseEntity<Object> removeProdFromCart(@PathVariable Long productId, @RequestBody Integer amount){
		Product addedProd = service.removeProdFromCart(productId, amount);
		if(addedProd == null) {
			return new ResponseEntity<>("Error: invalid amount or product not found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Returned " + amount + " unit(s) of the product " + addedProd.getName(), HttpStatus.CREATED); 
	}
	
	@PutMapping("/api/cart/purchase")
	public ResponseEntity<Object> purchaseCart(){
		List<CartItem> cart = service.getCart();
		if(cart.isEmpty()) {
			return new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.purchaseCart(), HttpStatus.OK);
	}
	
	@PutMapping("/api/cart/clear")
	public ResponseEntity<Object> clearCart(){
		List<CartItem> cart = service.getCart();
		if(cart.isEmpty()) {
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
		Optional<Sale> saletSearch = service.findById(id);
		if(saletSearch.isPresent()) {
			service.deleteById(id);
			return new ResponseEntity<>("Sale of id " + id + " has been deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("There's no sale with such id.", HttpStatus.BAD_REQUEST);
	}
}
