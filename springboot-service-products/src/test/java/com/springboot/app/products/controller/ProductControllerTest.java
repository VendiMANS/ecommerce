package com.springboot.app.products.controller;

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

import com.springboot.app.products.model.Product;
import com.springboot.app.products.service.ProductService;

class ProductControllerTest {

	@Mock
	private ProductService service;
	
	@InjectMocks
	private ProductController control;
	
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
	
	
	
	
	
	//private List<Product> emptyList;
	
	private Product emptyProd;
	private Product fullProd1;
	private Product fullProd2;
	private Product offerProd;
	
	private List<Product> prodList1;
	//private List<Product> prodList2;
	
	private Map<Long, Double> idAndPricesA;
	private Map<Long, Double> idAndPricesB;
	private Map<Long, Integer> idAndStocksA;
	private Map<Long, Integer> idAndStocksB;
	
	private List<Long> ids;
	
	
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		emptyProdOpt = Optional.empty();
		
		errorNoProdFoundByIdResp = new ResponseEntity<>("There's no product with such id.", HttpStatus.BAD_REQUEST);
		errorNoProdFoundByNameResp = new ResponseEntity<>("There's no product with such name.", HttpStatus.BAD_REQUEST);
		errorProdAlreadySavedResp = new ResponseEntity<>("There's already a product with such name.", HttpStatus.BAD_REQUEST);
		errorNoProdFoundInContainerResp = new ResponseEntity<>("There's no product with any of those ids.", HttpStatus.BAD_REQUEST);
		errorEmptyList = new ResponseEntity<>("This list is empty", HttpStatus.BAD_REQUEST);
		
		//emptyList = new ArrayList<>();
		
		emptyProd = new Product();
		
		fullProd1 = new Product("Product A", 1.0, 2, false);
		fullProd1.setId(1L);
		
		fullProd2 = new Product("Product B", 1.0, 2, false);
		fullProd2.setId(2L);
		
		offerProd = new Product("Product on sale", 1.0, 2, true);
		offerProd.setId(3L);
		
		prodList1 = new ArrayList<>();
		//prodList2 = new ArrayList<>();
		
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
	void testFindByIdNull() {
		when(service.findById(1L)).thenReturn(emptyProdOpt);
		
		assertEquals(errorNoProdFoundByIdResp, control.findById(1L));
	}
	
	@Test
	void testFindByIdOk() {
		when(service.findById(1L)).thenReturn(Optional.of(fullProd1));
		
		assertEquals(prodFoundResp(fullProd1), control.findById(1L));
	}

	@Test
	void testFindOffersEmpty() {
		when(service.findOffers()).thenReturn(prodList1);
		
		assertEquals(errorEmptyList, control.findOffers());
	}
	
	@Test
	void testFindOffersOk() {
		prodList1.add(offerProd);
		when(service.findOffers()).thenReturn(prodList1);
		
		assertEquals(prodFoundListResp(prodList1), control.findOffers());
	}
	
	@Test
	void testFindListEmpty() {
		when(service.findList(ids)).thenReturn(prodList1);
		
		assertEquals(errorEmptyList, control.findList(ids));
	}
	
	@Test
	void testFindListOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		
		when(service.findList(ids)).thenReturn(prodList1);
		
		assertEquals(prodFoundListResp(prodList1), control.findList(ids));
	}

	@Test
	void testFindAllEmpty() {
		when(service.findAll()).thenReturn(prodList1);
		
		assertEquals(errorEmptyList, control.findAll());
	}
	
	@Test
	void testFindAllOk() {
		prodList1.add(emptyProd);
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		prodList1.add(offerProd);
		when(service.findAll()).thenReturn(prodList1);
		
		assertEquals(prodFoundListResp(prodList1), control.findAll());
	}

	@Test
	void testSaveNull() {
		when(service.findByName("Product A")).thenReturn(Optional.of(fullProd1));
		
		assertEquals(errorProdAlreadySavedResp, control.save(fullProd1));
	}
	
	@Test
	void testSaveOk() {
		when(service.findByName("Product A")).thenReturn(emptyProdOpt);
		when(service.save(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.save(fullProd1));
	}

	@Test
	void testSaveAllEmpty() {
		when(service.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(errorEmptyList, control.saveAll(prodList1));
	}
	
	@Test
	void testSaveAllOk() {
		prodList1.add(emptyProd);
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		prodList1.add(offerProd);
		when(service.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.saveAll(prodList1));
	}

	@Test
	void testEditNull() {
		when(service.edit(fullProd1, 1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.edit(fullProd1, 1L));
	}
	
	@Test
	void testEditOk() {
		when(service.edit(fullProd1, 1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.edit(fullProd1, 1L));
	}

	@Test
	void testEditPriceNull() {
		when(service.editPrice(10.0, 1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.editPrice(10.0, 1L));
	}
	
	@Test
	void testEditPriceOk() {
		when(service.editPrice(10.0, 1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.editPrice(10.0, 1L));
	}

	@Test
	void testEditPriceMapEmpty() {
		when(service.editPriceMap(idAndPricesB)).thenReturn(prodList1);
		
		assertEquals(errorNoProdFoundInContainerResp, control.editPriceMap(idAndPricesB));
	}
	
	@Test
	void testEditPriceMapOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		when(service.editPriceMap(idAndPricesA)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.editPriceMap(idAndPricesA));
	}

	@Test
	void testPutOnSaleNull() {
		when(service.putOnSale(1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.putOnSale(1L));
	}
	
	@Test
	void testPutOnSaleOk() {
		when(service.putOnSale(1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.putOnSale(1L));
	}

	@Test
	void testPutOnSaleListEmpty() {
		when(service.putOnSaleList(ids)).thenReturn(prodList1);
		
		assertEquals(errorNoProdFoundInContainerResp, control.putOnSaleList(ids));
	}
	
	@Test
	void testPutOnSaleListOk() {
		prodList1.add(offerProd);
		when(service.putOnSaleList(ids)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.putOnSaleList(ids));
	}

	@Test
	void testRemoveOnSaleNull() {
		when(service.removeOnSale(1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.removeOnSale(1L));
	}
	
	@Test
	void testRemoveOnSaleOk() {
		when(service.removeOnSale(1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.removeOnSale(1L));
	}

	@Test
	void testRemoveOnSaleListEmpty() {
		when(service.removeOnSaleList(ids)).thenReturn(prodList1);
		
		assertEquals(errorNoProdFoundInContainerResp, control.removeOnSaleList(ids));
	}
	
	@Test
	void testRemoveOnSaleListOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		when(service.removeOnSaleList(ids)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.removeOnSaleList(ids));
	}

	@Test
	void testAddStockNull() {
		when(service.addStock(1, 1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.addStock(1, 1L));
	}
	
	@Test
	void testAddStockOk() {
		when(service.addStock(1, 1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.addStock(1, 1L));
	}
/*
	@Test
	void testAddStockMapEmpty() {
		when(service.addStockMap(idAndStocksB)).thenReturn(prodList1);
		
		assertEquals(errorNoProdFoundInContainerResp, control.addStockMap(idAndStocksB));
	}
	
	@Test
	void testAddStockMapOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		when(service.addStockMap(idAndStocksB)).thenReturn(prodList1);
		
		assertEquals(prodSaveListResp(prodList1), control.addStockMap(idAndStocksB));		
	}
*/
	@Test
	void testSellStockNull() {
		when(service.sellStock(1, 1l)).thenReturn(null);
		ResponseEntity<Object> resp = new ResponseEntity<>("Not enough stock or invalid id.", HttpStatus.BAD_REQUEST);
		assertEquals(resp, control.sellStock(1, 1L));
	}
	
	@Test
	void testRemoveStockOk() {
		when(service.sellStock(1, 1l)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.sellStock(1, 1L));
	}

	@Test
	void testDeleteAll() {
		ResponseEntity<Object> resp = new ResponseEntity<>("All products have been deleted.", HttpStatus.OK);
		assertEquals(resp, control.deleteAll());
	}

	@Test
	void testDeleteByIdNull() {
		assertEquals(errorNoProdFoundByIdResp, control.deleteById(1L));
	}
	
	@Test
	void testDeleteByIdOk() {
		Long id = 1L;
		when(service.findById(id)).thenReturn(Optional.of(fullProd1));
		ResponseEntity<Object> resp = new ResponseEntity<>("Product of id " + id + " has been deleted.", HttpStatus.OK);
		assertEquals(resp, control.deleteById(1L));
	}

}
