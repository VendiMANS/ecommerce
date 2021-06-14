package com.springboot.app.ecommerce.service;

import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.Sale;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springboot.app.ecommerce.repository.ISaleRepository;

class EcommerceServiceTest {
/*
	@Mock
	private ISaleRepository repository;
	
	@Mock
	private RestTemplate client;
	
	//@Mock
	//private Map<Long, Integer> cart = new HashMap<>();
	
	@InjectMocks
	private EcommerceService service;
	
	
	
	private Sale fullSale1;
	private Sale fullSale2;
	
	private List<Sale> saleList;
	
	
	
	private Optional<Product> emptyProdOpt;
	
	private ResponseEntity<Product> errorNoProdFoundByIdRespGet;
	
	private ResponseEntity<Object> errorNoProdFoundByIdResp;
	private ResponseEntity<Object> errorNoProdFoundByNameResp;
	private ResponseEntity<Object> errorProdAlreadySavedResp;
	private ResponseEntity<Object> errorNoProdFoundInContainerResp;
	private ResponseEntity<Object> errorEmptyList;
	
	private ResponseEntity<Product> prodFoundRespGet(Product prod){
		return new ResponseEntity<>(prod, HttpStatus.OK);
	}
	
	private ResponseEntity<Object> prodFoundResp(Product prod){
		return new ResponseEntity<>(prod, HttpStatus.OK);
	}
	
	private ResponseEntity<Object> prodFoundListResp(List<Product> prods){
		return new ResponseEntity<>(prods, HttpStatus.OK);
	}
	
	private ResponseEntity<Object> prodSavedResp(Product prod){
		return new ResponseEntity<>(prod, HttpStatus.CREATED);
	}
	
	private ResponseEntity<Object> prodSavedListResp(List<Product> prods){
		return new ResponseEntity<>(prods, HttpStatus.CREATED);
	}
	
	
	private ResponseEntity<Boolean> boolResp(Boolean bool){
		return new ResponseEntity<>(bool, HttpStatus.OK);
	}
	
	
	private Product emptyProd;
	private Product fullProd1;
	private Product fullProd2;
	private Product offerProd;
	
	private List<Product> prodList1;
	
	private Map<Long, Double> idAndPricesA;
	private Map<Long, Double> idAndPricesB;
	private Map<Long, Integer> idAndStocksA;
	private Map<Long, Integer> idAndStocksB;
	
	private List<Long> ids;
	
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		service.clearCart();
		
		
		
		fullSale1 = new Sale("Product A", 1);
		fullSale1.setId(1L);
		fullSale2 = new Sale("Product B", 1);
		fullSale2.setId(2L);
		
		saleList = new ArrayList<>();
		
		
		
		emptyProdOpt = Optional.empty();
		
		
		
		errorNoProdFoundByIdRespGet = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
		errorNoProdFoundByIdResp = new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
		errorNoProdFoundByNameResp = new ResponseEntity<>("There's no product with such name.", HttpStatus.BAD_REQUEST);
		errorProdAlreadySavedResp = new ResponseEntity<>("There's already a product with such name.", HttpStatus.BAD_REQUEST);
		errorNoProdFoundInContainerResp = new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
		errorEmptyList = new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
		
		emptyProd = new Product();
		
		fullProd1 = new Product("Product A", 1.0, 2, false);
		fullProd1.setId(1L);
		
		fullProd2 = new Product("Product B", 1.0, 2, false);
		fullProd2.setId(2L);
		
		offerProd = new Product("Product on sale", 1.0, 2, true);
		offerProd.setId(3L);
		
		prodList1 = new ArrayList<>();
		
		idAndPricesA = new HashMap<>();
		idAndPricesA.put(1L, 10.0);
		idAndPricesA.put(2L, 20.0);
		idAndPricesB = new HashMap<>();
		idAndPricesB.put(3L, 30.0);
		idAndPricesB.put(4L, 40.0);
		idAndStocksA = new HashMap<>();
		idAndStocksA.put(1L, 1);
		idAndStocksA.put(2L, 2);
		idAndStocksB = new HashMap<>();
		idAndStocksB.put(3L, 3);
		idAndStocksB.put(4L, 4);
		
		ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		
	}
	
	
	private void stubProductGet(Long id, ResponseEntity<Product> resp) {
		
		String uri = "http://localhost:8001/getProduct/{id}";
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", id.toString());
		
	    when(client.getForEntity(
				uri,
				Product.class,
				params))
		.thenReturn(resp);
		
	}
	
	
	private void stubProductFindById(Long id, ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/find/id/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
		
	    when(client.getForEntity(
				uri,
				Object.class,
				params))
		.thenReturn(resp);
		
	}
	
	private void stubProductFindByName(String name, ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/find/name";
		
		Map<String, String> params = new HashMap<String, String>();
	    params.put("name", name);
		
		when(client.getForEntity(
				uri,
				Object.class,
				params))
		.thenReturn(resp);
		
	}
	
	private void stubProductFindOffers(ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/find/offers";
		
		when(client.getForEntity(
				uri,
				Object.class))
		.thenReturn(resp);
		
	}
	
	private void stubProductFindAll(ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/find/all";
		
		when(client.getForEntity(
				uri,
				Object.class))
		.thenReturn(resp);
		
	}
	
	private void stubProductSave(Product product, ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/save";
		
		when(client.postForObject(
				uri,
				product,
				ResponseEntity.class))
		.thenReturn(resp);
	}
	
	private void stubProductSaveAll(List<Product> products, ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/save/all";
		
		when(client.postForObject(
				uri,
				products,
				ResponseEntity.class))
		.thenReturn(resp);
	}
	
	private void stubProductEditPrice(Double price, Long id, ResponseEntity<Object> resp) {
		
		String uri = "http://localhost:8001/edit/price/{id}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    params.put("price", price);
	    
	    when(client.postForObject(
	    		uri,
	    		params,
	    		ResponseEntity.class))
	    .thenReturn(resp);
	}
	
	private void stubProductHasEnoughStockTrue(Integer amount, Long id) {
		
		String uri = "http://localhost:8001/hasEnoughStockToSell/{id}/{stockToSell}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    params.put("stockToSell", amount);
	    
	    when(client.getForEntity(
						uri,
						Boolean.class,
						params)).
	    thenReturn(boolResp(true));
		
	}
	
private void stubProductHasEnoughStockFalse(Integer amount, Long id) {
		
		String uri = "http://localhost:8001/hasEnoughStockToSell/{id}/{stockToSell}";
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    params.put("stockToSell", amount);
	    
	    when(client.getForEntity(
					uri,
					Boolean.class,
					params)).
	    thenReturn(boolResp(false));
		
	}
	
	
	
	


	@Test
	void testFindAllByMonthNull() {
		assertNull(service.findAllByMonth(13));
		assertNull(service.findAllByMonth(0));
	}
	
	@Test
	void testFindAllByMonthOk() {
		when(repository.findAllByMonth(6)).thenReturn(saleList);
		
		assertEquals(saleList, service.findAllByMonth(6));
	}

	@Test
	void testFindAllEmpty() {
		when(repository.findAll()).thenReturn(saleList);
		
		assertEquals(saleList, service.findAll());
	}
	
	@Test
	void testFindAllOk() {
		saleList.add(fullSale1);
		when(repository.findAll()).thenReturn(saleList);
		
		assertEquals(saleList, service.findAll());
	}

	@Test
	void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullSale1));
		when(repository.findById(2L)).thenReturn(Optional.of(fullSale2));
		when(repository.findById(3L)).thenReturn(Optional.empty());
		
		assertEquals(Optional.of(fullSale1), service.findById(1L));
		assertEquals(Optional.of(fullSale2), service.findById(2L));
		assertEquals(Optional.empty(), service.findById(3L));
	}

	@Test
	void testDeleteAll() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullSale1));
		
		assertEquals(Optional.of(fullSale1), service.findById(1L));
		
		service.deleteAll();
		
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertEquals(Optional.empty(), service.findById(1L));
	}

	@Test
	void testDeleteById() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullSale1));
		
		assertEquals(Optional.of(fullSale1), service.findById(1L));
		
		service.deleteById(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertEquals(Optional.empty(), service.findById(1L));
	}

	@Test
	void testGetCartNull() {
		
		stubProductGet(1L, errorNoProdFoundByIdRespGet);
		stubProductGet(2L, errorNoProdFoundByIdRespGet);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testGetCartOk() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		CartItem item2 = new CartItem(fullProd2,2);
		res.add(item1);
		res.add(item2);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		stubProductFindById(2L, prodFoundResp(fullProd2));
		
		stubProductHasEnoughStockTrue(1, 1L);
		stubProductHasEnoughStockTrue(2, 2L);
		
		service.addProdToCart(1L, 1);
		service.addProdToCart(2L, 2);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		stubProductGet(2L, prodFoundRespGet(fullProd2));
		
		List<CartItem> serviceCart = service.getCart();
		
		assertEquals(res, serviceCart);
	}

	@Test
	void testAddProdToCartInvalidAmount() {
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		
		stubProductHasEnoughStockTrue(0, 1L);
		
		service.addProdToCart(1L, 0);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testAddProdToCartRemoved() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		

		stubProductFindById(1L, prodFoundResp(fullProd1));
		
		stubProductHasEnoughStockTrue(1, 1L);
		
		service.addProdToCart(1L, 1);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		
		assertEquals(res, service.getCart());
		
		stubProductFindById(1L, errorNoProdFoundByIdResp);
		service.addProdToCart(1L, 1);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testAddProdToCartNotEnoughStock() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		
		stubProductHasEnoughStockTrue(1, 1L);
		
		service.addProdToCart(1L, 1);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		
		assertEquals(res, service.getCart());
		
		
		
		stubProductHasEnoughStockFalse(1, 1L);
		
		service.addProdToCart(1L, 1);
		
		assertEquals(res, service.getCart());
	}
	
	@Test
	void testAddProdToCartOk() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		stubProductHasEnoughStockTrue(1, 1L);
		service.addProdToCart(1L, 1);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		assertEquals(res, service.getCart());
		
		CartItem item2 = new CartItem(fullProd2,2);
		res.add(item2);
		
		stubProductFindById(2L, prodFoundResp(fullProd2));
		stubProductHasEnoughStockTrue(2, 2L);
		service.addProdToCart(2L, 2);
		
		stubProductGet(2L, prodFoundRespGet(fullProd2));
		assertEquals(res, service.getCart());
	}

	@Test
	void testRemoveProdFromCartInvalidAmount() {
		stubProductFindById(1L, prodFoundResp(fullProd1));
		service.removeProdFromCart(1L, 0);
		assertNull(service.getCart());
	}
	
	@Test
	void testRemoveProdFromCartRemoved() {
		List<CartItem> res = new ArrayList<>();
		CartItem item = new CartItem(fullProd1,1);
		res.add(item);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		stubProductHasEnoughStockTrue(1, 1L);
		service.addProdToCart(1L, 1);
		
		stubProductFindById(1L, errorNoProdFoundByIdResp);
		service.removeProdFromCart(1L, 1);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testRemoveProdFromCartNotEnoughStock() {
		List<CartItem> res = new ArrayList<>();
		CartItem item = new CartItem(fullProd1,1);
		res.add(item);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		stubProductHasEnoughStockTrue(1, 1L);
		service.addProdToCart(1L, 1);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		assertEquals(res, service.getCart());
		
		//stubProductFindById(1L, errorNoProdFoundByIdResp);
		service.removeProdFromCart(1L, 1);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testRemoveProdFromCartOk() {
		List<CartItem> res1 = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,2);
		res1.add(item1);
		
		List<CartItem> res2 = new ArrayList<>();
		CartItem item2 = new CartItem(fullProd1,1);
		res2.add(item2);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		
		stubProductHasEnoughStockTrue(2, 1L);
		service.addProdToCart(1L, 2);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		assertEquals(res1, service.getCart());
		
		service.removeProdFromCart(1L, 1);
		
		assertEquals(res2, service.getCart());
		
	}
	

	@Test
	void testPurchaseCart() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,2);
		res.add(item1);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		
		stubProductHasEnoughStockTrue(2, 1L);
		service.addProdToCart(1L, 2);
		
		assertEquals(res,service.purchaseCart());
	}

	@Test
	void testClearCart() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		
		stubProductFindById(1L, prodFoundResp(fullProd1));
		
		stubProductHasEnoughStockTrue(1, 1L);
		service.addProdToCart(1L, 1);
		
		stubProductGet(1L, prodFoundRespGet(fullProd1));
		assertEquals(res, service.getCart());
		
		service.clearCart();
		
		assertNull(service.getCart());
	}
*/
}
