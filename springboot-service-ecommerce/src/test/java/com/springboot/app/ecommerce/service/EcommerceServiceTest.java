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

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springboot.app.ecommerce.client.IFeignClient;
import com.springboot.app.ecommerce.repository.ISaleRepository;

class EcommerceServiceTest {

	@Mock
	private ISaleRepository repository;
	
	@Mock
	private IFeignClient client;
	
	@InjectMocks
	private EcommerceService service;
	
	
	
	private Sale fullSale1;
	private Sale fullSale2;
	
	private List<Sale> saleList;
	
	
	
	private Optional<Product> emptyProdOpt;
	
	private ResponseEntity<Object> errorNoProdFoundByIdResp;
	private ResponseEntity<Object> errorNoProdFoundByNameResp;
	private ResponseEntity<Object> errorProdAlreadySavedResp;
	private ResponseEntity<Object> errorNoProdFoundInContainerResp;
	private ResponseEntity<Object> errorEmptyList;
	
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
	
	
	
	@Test
	void testProductSaveNull() {
		when(client.save(fullProd1)).thenReturn(errorProdAlreadySavedResp);
		
		assertNull(service.productSave(fullProd1));
	}
	
	@Test
	void testProductSaveOk() {
		when(client.save(fullProd2)).thenReturn(prodSavedResp(fullProd2));
		
		assertEquals(fullProd2,service.productSave(fullProd2));
	}

	@Test
	void testProductSaveAllEmpty() {
		prodList1.add(emptyProd);
		when(client.saveAll(prodList1)).thenReturn(errorEmptyList);
		
		assertNull(service.productSaveAll(prodList1));
	}
	
	@Test
	void testProductSaveAllOk() {
		prodList1.add(emptyProd);
		when(client.saveAll(prodList1)).thenReturn(prodSavedListResp(prodList1));
		
		assertEquals(prodList1,service.productSaveAll(prodList1));
	}

	@Test
	void testProductEditPriceNull() {
		when(client.editPrice(1.0,1L)).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.productEditPrice(1.0,1L));
	}
	
	@Test
	void testProductEditPriceOk() {
		when(client.editPrice(2.0,2L)).thenReturn(prodSavedResp(fullProd2));
		
		assertEquals(fullProd2,service.productEditPrice(2.0,2L));
	}

	@Test
	void testProductEditPriceMapEmpty() {
		when(client.editPriceMap(idAndPricesA)).thenReturn(errorEmptyList);
		
		assertNull(service.productEditPriceMap(idAndPricesA));
	}
	
	@Test
	void testProductEditPriceMapOk() {
		prodList1.add(emptyProd);
		when(client.editPriceMap(idAndPricesA)).thenReturn(prodSavedListResp(prodList1));
		
		assertEquals(prodList1,service.productEditPriceMap(idAndPricesA));
	}

	@Test
	void testProductPutOnSaleNull() {
		when(client.putOnSale(1L)).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.productPutOnSale(1L));
	}
	
	@Test
	void testProductPutOnSaleOk() {
		when(client.putOnSale(2L)).thenReturn(prodSavedResp(fullProd2));
		
		assertEquals(fullProd2,service.productPutOnSale(2L));
	}

	@Test
	void testProductPutOnSaleListEmpty() {
		when(client.putOnSaleList(ids)).thenReturn(errorEmptyList);
		
		assertNull(service.productPutOnSaleList(ids));
	}
	
	@Test
	void testProductPutOnSaleListOk() {
		when(client.putOnSaleList(ids)).thenReturn(prodSavedListResp(prodList1));
		
		assertEquals(prodList1,service.productPutOnSaleList(ids));
	}

	@Test
	void testProductRemoveOnSaleNull() {
		when(client.removeOnSale(1L)).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.productRemoveOnSale(1L));
	}
	
	@Test
	void testProductRemoveOnSaleOk() {
		when(client.removeOnSale(2L)).thenReturn(prodSavedResp(fullProd2));
		
		assertEquals(fullProd2,service.productRemoveOnSale(2L));
	}

	@Test
	void testProductRemoveOnSaleListEmpty() {
		when(client.removeOnSaleList(ids)).thenReturn(errorEmptyList);
		
		assertNull(service.productRemoveOnSaleList(ids));
	}
	
	@Test
	void testProductRemoveOnSaleListOk() {
		when(client.removeOnSaleList(ids)).thenReturn(prodSavedListResp(prodList1));
		
		assertEquals(prodList1,service.productRemoveOnSaleList(ids));
	}

	@Test
	void testProductAddStockNull() {
		when(client.addStock(11,1L)).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.productAddStock(11,1L));
	}
	
	@Test
	void testProductAddStockOk() {
		when(client.addStock(22,2L)).thenReturn(prodSavedResp(fullProd2));
		
		assertEquals(fullProd2,service.productAddStock(22,2L));
	}

	@Test
	void testProductFindByIdNull() {
		when(client.findById(1L)).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.productFindById(1L));
	}
	
	@Test
	void testProductFindByIdOk() {
		when(client.findById(2L)).thenReturn(prodFoundResp(fullProd2));
		
		assertEquals(fullProd2,service.productFindById(2L));
	}

	@Test
	void testProductFindByNameNull() {
		when(client.findByName("A")).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.productFindByName("A"));
	}
	
	@Test
	void testProductFindByNameOk() {
		when(client.findByName("B")).thenReturn(prodFoundResp(fullProd2));
		
		assertEquals(fullProd2,service.productFindByName("B"));
	}

	@Test
	void testProductFindAllEmpty() {
		when(client.findAll()).thenReturn(errorEmptyList);
		
		assertNull(service.productFindAll());
	}
	
	@Test
	void testProductFindAllOk() {
		when(client.findAll()).thenReturn(prodFoundListResp(prodList1));
		
		assertEquals(prodList1,service.productFindAll());
	}

	@Test
	void testProductFindOffersEmpty() {
		when(client.findOffers()).thenReturn(errorEmptyList);
		
		assertNull(service.productFindOffers());
	}
	
	@Test
	void testProductFindOffersOk() {
		when(client.findOffers()).thenReturn(prodFoundListResp(prodList1));
		
		assertEquals(prodList1,service.productFindOffers());
	}

	@Test
	void testProductDeleteByIdNull() {
		when(client.findById(1L)).thenReturn(errorNoProdFoundByIdResp);
		
		service.productDeleteById(1L);
	}
	
	@Test
	void testProductDeleteByIdOk() {
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		
		service.productDeleteById(1L);
	}

	@Test
	void testProductDeleteAll() {
		service.productDeleteAll();
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
		when(client.findById(1L)).thenReturn(errorNoProdFoundByIdResp);
		when(client.findById(2L)).thenReturn(errorNoProdFoundByIdResp);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testGetCartOk() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		CartItem item2 = new CartItem(fullProd2,2);
		res.add(item1);
		res.add(item2);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		when(client.findById(2L)).thenReturn(prodFoundResp(fullProd2));
		
		service.addProdToCart(1L, 1);
		service.addProdToCart(2L, 2);
		
		List<CartItem> serviceCart = service.getCart();
		
		assertEquals(res, serviceCart);
	}

	@Test
	void testAddProdToCartInvalidAmount() {
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 0);
		assertNull(service.getCart());
	}
	
	@Test
	void testAddProdToCartRemoved() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 1);
		
		assertEquals(res, service.getCart());
		
		when(client.findById(1L)).thenReturn(errorNoProdFoundByIdResp);
		service.addProdToCart(1L, 1);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testAddProdToCartNotEnoughStock() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,2);
		res.add(item1);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		
		service.addProdToCart(1L, 1);
		fullProd1.setStock(1);
		service.addProdToCart(1L, 1);
		fullProd1.setStock(0);
		
		assertEquals(res, service.getCart());
		
		service.addProdToCart(1L, 1);
		
		assertEquals(res, service.getCart());
	}
	
	@Test
	void testAddProdToCartOk() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 1);
		
		assertEquals(res, service.getCart());
		
		CartItem item2 = new CartItem(fullProd2,2);
		res.add(item2);
		
		when(client.findById(2L)).thenReturn(prodFoundResp(fullProd2));
		service.addProdToCart(2L, 2);
		
		assertEquals(res, service.getCart());
	}

	@Test
	void testRemoveProdFromCartInvalidAmount() {
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.removeProdFromCart(1L, 0);
		assertNull(service.getCart());
	}
	
	@Test
	void testRemoveProdFromCartRemoved() {
		List<CartItem> res = new ArrayList<>();
		CartItem item = new CartItem(fullProd1,1);
		res.add(item);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 1);
		
		when(client.findById(1L)).thenReturn(errorNoProdFoundByIdResp);
		service.removeProdFromCart(1L, 1);
		
		assertNull(service.getCart());
	}
	
	@Test
	void testRemoveProdFromCartNotEnoughStock() {
		List<CartItem> res = new ArrayList<>();
		CartItem item = new CartItem(fullProd1,1);
		res.add(item);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 1);
		
		assertEquals(res, service.getCart());
		
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
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 2);
		
		assertEquals(res1, service.getCart());
		
		service.removeProdFromCart(1L, 1);
		
		assertEquals(res2, service.getCart());
		
	}
	

	@Test
	void testPurchaseCart() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,2);
		res.add(item1);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 2);
		
		assertEquals(res,service.purchaseCart());
	}

	@Test
	void testClearCart() {
		List<CartItem> res = new ArrayList<>();
		CartItem item1 = new CartItem(fullProd1,1);
		res.add(item1);
		
		when(client.findById(1L)).thenReturn(prodFoundResp(fullProd1));
		service.addProdToCart(1L, 1);
		
		assertEquals(res, service.getCart());
		
		service.clearCart();
		
		assertNull(service.getCart());
	}

}
