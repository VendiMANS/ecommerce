package com.springboot.app.ecommerce.controller;

import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Product;
import com.springboot.app.ecommerce.model.Sale;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.app.ecommerce.service.EcommerceService;

class EcommerceControllerTest {

	@Mock
	private EcommerceService service;
	
	@InjectMocks
	private EcommerceController control;
	
	
	private Sale fullSale1;
	private Sale fullSale2;
	
	private List<Sale> saleList;
	
	private ResponseEntity<Object> errorNoSaleFoundByIdResp;
	
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
	
	
	
	private ResponseEntity<Object> saleFoundResp(Sale sale){
		return new ResponseEntity<>(sale, HttpStatus.OK);
	}
	
	private ResponseEntity<Object> saleFoundListResp(List<Sale> sales){
		return new ResponseEntity<>(sales, HttpStatus.OK);
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
	
	
	private CartItem item1;
	private CartItem item2;
	
	private List<CartItem> emptyCart;
	private List<CartItem> fullCart;
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		fullSale1 = new Sale("Product A", 1);
		fullSale1.setId(1L);
		fullSale2 = new Sale("Product B", 1);
		fullSale2.setId(2L);
		
		saleList = new ArrayList<>();
		
		
		errorNoSaleFoundByIdResp = new ResponseEntity<>("There's no sale with such id.", HttpStatus.BAD_REQUEST);
		
		
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
		
		item1 = new CartItem(fullProd1, 1);
		item2 = new CartItem(fullProd2, 2);
		
		emptyCart = new ArrayList<>();
		fullCart = new ArrayList<>();
		fullCart.add(item1);
		fullCart.add(item2);
	}
	
	
	
	@Test
	void testProductSaveError() {
		when(service.productSave(fullProd1)).thenReturn(null);
		
		assertEquals(errorProdAlreadySavedResp, control.productSave(fullProd1));
	}
	
	@Test
	void testProductSaveOk() {
		when(service.productSave(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.productSave(fullProd1));
	}

	@Test
	void testProductSaveAllEmpty() {
		when(service.productSaveAll(prodList1)).thenReturn(null);
		
		assertEquals(errorEmptyList, control.productSaveAll(prodList1));
	}
	
	@Test
	void testProductSaveAllOk() {
		when(service.productSaveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.productSaveAll(prodList1));
	}

	@Test
	void testProductEditPriceNull() {
		when(service.productEditPrice(2.5,1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.productEditPrice(2.5,1L));
	}
	
	@Test
	void testProductEditPriceOk() {
		when(service.productEditPrice(5.5,1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.productEditPrice(5.5,1L));
	}

	@Test
	void testProductEditPriceMapEmpty() {
		when(service.productEditPriceMap(idAndPricesA)).thenReturn(null);
		
		assertEquals(errorNoProdFoundInContainerResp, control.productEditPriceMap(idAndPricesA));
	}
	
	@Test
	void testProductEditPriceMapOk() {
		when(service.productEditPriceMap(idAndPricesA)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.productEditPriceMap(idAndPricesA));
	}

	@Test
	void testProductPutOnSaleNull() {
		when(service.productPutOnSale(1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.productPutOnSale(1L));
	}
	
	@Test
	void testProductPutOnSaleOk() {
		when(service.productPutOnSale(1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.productPutOnSale(1L));
	}

	@Test
	void testProductPutOnSaleListEmpty() {
		when(service.productPutOnSaleList(ids)).thenReturn(null);
		
		assertEquals(errorNoProdFoundInContainerResp, control.productPutOnSaleList(ids));
	}
	
	@Test
	void testProductPutOnSaleListOk() {
		when(service.productPutOnSaleList(ids)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.productPutOnSaleList(ids));
	}

	@Test
	void testProductRemoveOnSaleNull() {
		when(service.productRemoveOnSale(1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.productRemoveOnSale(1L));
	}
	
	@Test
	void testProductRemoveOnSaleOk() {
		when(service.productRemoveOnSale(2L)).thenReturn(fullProd2);
		
		assertEquals(prodSavedResp(fullProd2), control.productRemoveOnSale(2L));
	}

	@Test
	void testProductRemoveOnSaleListEmpty() {
		when(service.productRemoveOnSaleList(ids)).thenReturn(null);
		
		assertEquals(errorNoProdFoundInContainerResp, control.productRemoveOnSaleList(ids));
	}
	
	@Test
	void testProductRemoveOnSaleListOk() {
		when(service.productRemoveOnSaleList(ids)).thenReturn(prodList1);
		
		assertEquals(prodSavedListResp(prodList1), control.productRemoveOnSaleList(ids));
	}


	@Test
	void testProductAddStockNull() {
		when(service.productAddStock(1,1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.productAddStock(1,1L));
	}
	
	@Test
	void testProductAddStockOk() {
		when(service.productAddStock(1,1L)).thenReturn(fullProd1);
		
		assertEquals(prodSavedResp(fullProd1), control.productAddStock(1,1L));
	}

	@Test
	void testProductFindByIdNull() {
		when(service.productFindById(1L)).thenReturn(null);
		
		assertEquals(errorNoProdFoundByIdResp, control.productFindById(1L));
	}
	
	@Test
	void testProductFindByIdOk() {
		when(service.productFindById(2L)).thenReturn(fullProd2);
		
		assertEquals(prodFoundResp(fullProd2), control.productFindById(2L));
	}

	@Test
	void testProductFindByNameNull() {
		when(service.productFindByName("A")).thenReturn(null);
		
		assertEquals(errorNoProdFoundByNameResp, control.productFindByName("A"));
	}
	
	@Test
	void testProductFindByNameOk() {
		when(service.productFindByName("B")).thenReturn(fullProd2);
		
		assertEquals(prodFoundResp(fullProd2), control.productFindByName("B"));
	}

	@Test
	void testProductFindOffersEmpty() {
		when(service.productFindOffers()).thenReturn(null);
		
		assertEquals(errorEmptyList, control.productFindOffers());
	}
	
	@Test
	void testProductFindAllEmptyOffers() {
		when(service.productFindOffers()).thenReturn(prodList1);
		
		assertEquals(prodFoundListResp(prodList1), control.productFindOffers());
	}

	@Test
	void testProductFindAllEmpty() {
		when(service.productFindAll()).thenReturn(null);
		
		assertEquals(errorEmptyList, control.productFindAll());
	}
	
	@Test
	void testProductFindAllEmptyOk() {
		when(service.productFindAll()).thenReturn(prodList1);
		
		assertEquals(prodFoundListResp(prodList1), control.productFindAll());
	}

	@Test
	void testCartListEmpty() {
		when(service.getCart()).thenReturn(emptyCart);
		
		ResponseEntity<Object> resp = new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		
		assertEquals(resp, control.getCart());
	}
	
	@Test
	void testCartListOk() {
		when(service.getCart()).thenReturn(fullCart);
		List<CartItem> cart = service.getCart();
		
		ResponseEntity<Object> resp = new ResponseEntity<>(cart, HttpStatus.OK);
		
		assertEquals(resp, control.getCart());
	}

	@Test
	void testAddProdToCartError() {
		when(service.addProdToCart(1L, 1)).thenReturn(null);
		
		assertEquals(new ResponseEntity<>("Error: invalid amount or product not found", HttpStatus.BAD_REQUEST), control.addProdToCart(1L, 1));
	}

	@Test
	void testAddProdToCartOk() {
		when(service.addProdToCart(1L, 12)).thenReturn(fullProd1);
		
		assertEquals(new ResponseEntity<>("Added " + 12 + " unit(s) of the product " + "Product A" , HttpStatus.CREATED), control.addProdToCart(1L, 12));
	}
	
	@Test
	void testRemoveProdFromCartError() {
		when(service.removeProdFromCart(1L, 1)).thenReturn(null);
		
		assertEquals(new ResponseEntity<>("Error: invalid amount or product not found", HttpStatus.BAD_REQUEST), control.removeProdFromCart(1L, 1));
	}
	
	@Test
	void testRemoveProdFromCartOk() {
		when(service.removeProdFromCart(1L, 1)).thenReturn(fullProd1);
		
		assertEquals(new ResponseEntity<>("Returned " + 1 + " unit(s) of the product " + "Product A" , HttpStatus.CREATED), control.removeProdFromCart(1L, 1));
	}

	@Test
	void testPurchaseCartEmpty() {
		when(service.getCart()).thenReturn(emptyCart);
		
		ResponseEntity<Object> resp = new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		
		assertEquals(resp, control.purchaseCart());
	}
	
	@Test
	void testPurchaseCartOk() {
		when(service.getCart()).thenReturn(fullCart);
		when(service.purchaseCart()).thenReturn(fullCart);
		
		ResponseEntity<Object> resp = new ResponseEntity<>(fullCart, HttpStatus.OK);
		
		assertEquals(resp, control.purchaseCart());
	}

	@Test
	void testClearCartEmpty() {
		when(service.getCart()).thenReturn(emptyCart);
		
		ResponseEntity<Object> resp = new ResponseEntity<>("No products found. The cart may have been cleared or the products may no longer exist in the DB.", HttpStatus.BAD_REQUEST);
		
		assertEquals(resp, control.clearCart());
	}
	
	@Test
	void testClearCartOk() {
		when(service.getCart()).thenReturn(fullCart);
		
		ResponseEntity<Object> resp = new ResponseEntity<>("Shopping cart cleared.", HttpStatus.OK);
		
		assertEquals(resp, control.clearCart());
	}

	@Test
	void testFindAllEmpty() {
		when(service.findAll()).thenReturn(saleList);
		
		assertEquals(errorEmptyList, control.findAll());
	}
	
	@Test
	void testFindAllOk() {
		saleList.add(fullSale1);
		saleList.add(fullSale2);
		when(service.findAll()).thenReturn(saleList);
		
		assertEquals(saleFoundListResp(saleList), control.findAll());
	}

	@Test
	void testFindAllByMonthEmpty() {
		when(service.findAllByMonth(1)).thenReturn(null);
		
		ResponseEntity<Object> resp = new ResponseEntity<Object>("Invalid month", HttpStatus.BAD_REQUEST);
		
		assertEquals(resp, control.findAllByMonth(1));
	}
	
	@Test
	void testFindAllByMonthOk() {
		saleList.add(fullSale1);
		saleList.add(fullSale2);
		when(service.findAllByMonth(1)).thenReturn(saleList);
		
		assertEquals(saleFoundListResp(saleList), control.findAllByMonth(1));
	}

	@Test
	void testFindByIdNull() {
		when(service.findById(1L)).thenReturn(Optional.empty());
		
		assertEquals(errorNoSaleFoundByIdResp, control.findById(1L));
	}
	
	@Test
	void testFindByIdOk() {
		when(service.findById(1L)).thenReturn(Optional.of(fullSale1));
		
		assertEquals(saleFoundResp(fullSale1), control.findById(1L));
	}

	@Test
	void testDeleteAll() {
		ResponseEntity<Object> resp = new ResponseEntity<>("All sales have been deleted", HttpStatus.OK);
		assertEquals(resp, control.deleteAll());
	}

	@Test
	void testDeleteByIdNull() {
		when(service.findById(1L)).thenReturn(Optional.empty());
		assertEquals(errorNoSaleFoundByIdResp, control.deleteById(1L));
	}
	
	@Test
	void testDeleteByIdOk() {
		when(service.findById(1L)).thenReturn(Optional.of(fullSale1));
		ResponseEntity<Object> resp = new ResponseEntity<>("Sale of id " + 1 + " has been deleted", HttpStatus.OK);
		assertEquals(resp, control.deleteById(1L));
	}

}
