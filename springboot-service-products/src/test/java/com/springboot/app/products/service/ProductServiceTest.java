package com.springboot.app.products.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springboot.app.products.model.Product;
import com.springboot.app.products.repository.IProductRepository;

class ProductServiceTest {

	@Mock
	private IProductRepository repository;
	
	@InjectMocks
	private ProductService service;
	
	private List<Product> emptyList;
	
	private Product emptyProd;
	private Product fullProd1;
	private Product fullProd2;
	private Product offerProd;
	
	private List<Product> prodList1;
	private List<Product> prodList2;
	
	private Map<Long, Double> idAndPricesA;
	private Map<Long, Double> idAndPricesB;
	private Map<Long, Integer> idAndStocksA;
	private Map<Long, Integer> idAndStocksB;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
		
		emptyList = new ArrayList<>();
		
		emptyProd = new Product();
		
		fullProd1 = new Product("Product A", 1.0, 2, false);
		fullProd1.setId(1L);
		
		fullProd2 = new Product("Product B", 1.0, 2, false);
		fullProd2.setId(2L);
		
		offerProd = new Product("Product on sale", 1.0, 2, true);
		offerProd.setId(3L);
		
		prodList1 = new ArrayList<>();
		prodList2 = new ArrayList<>();
		
		idAndPricesA = new HashMap<>();
		idAndPricesA.put(1L, 10.0);
		idAndPricesA.put(2L, 20.0);
		idAndPricesB = new HashMap<>();
		idAndPricesB.put(3L, 30.0);
		idAndPricesB.put(4L, 40.0);
		idAndStocksA = new HashMap<>();
		idAndStocksA.put(1L, 1);
		idAndStocksA.put(2L, 22);
		idAndStocksB = new HashMap<>();
		idAndStocksB.put(3L, 3);
		idAndStocksB.put(4L, 4);
	}
	
	
	
	@Test
	public void testSave() {
		when(service.save(emptyProd)).thenReturn(emptyProd);
		when(service.save(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(emptyProd,service.save(emptyProd));
		assertEquals(fullProd1,service.save(fullProd1));
	}

	@Test
	public void testSaveAll() {
		List<Product> listToSave = new ArrayList<>();
		listToSave.add(fullProd2);
		
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		
		when(repository.findByName("Product A")).thenReturn(Optional.of(fullProd1));
		when(repository.findByName("Product B")).thenReturn(Optional.empty());
		when(repository.saveAll(listToSave)).thenReturn(listToSave);
		
		assertEquals(listToSave, service.saveAll(prodList1));
	}

	@Test
	public void testFindById() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.findById(2L)).thenReturn(Optional.of(fullProd2));
		when(repository.findById(3L)).thenReturn(Optional.empty());
		
		assertEquals(Optional.of(fullProd1), service.findById(1L));
		assertEquals(Optional.of(fullProd2), service.findById(2L));
		assertEquals(Optional.empty(), service.findById(3L));
	}

	@Test
	public void testFindByName() {
		when(repository.findByName("Product A")).thenReturn(Optional.of(fullProd1));
		when(repository.findByName("Product B")).thenReturn(Optional.of(fullProd2));
		when(repository.findByName("Product C")).thenReturn(Optional.empty());
		
		assertEquals(Optional.of(fullProd1), service.findByName("Product A"));
		assertEquals(Optional.of(fullProd2), service.findByName("Product B"));
		assertEquals(Optional.empty(),service.findByName("Product C"));
	}

	@Test
	public void testFindOffersEmpty() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		
		when(repository.findAll()).thenReturn(prodList1);
		
		assertEquals(prodList2, service.findOffers());
	}
	
	@Test
	public void testFindOffersOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		prodList1.add(offerProd);
		
		prodList2.add(offerProd);
		
		when(repository.findAll()).thenReturn(prodList1);
		
		assertEquals(prodList2, service.findOffers());
	}
	
	@Test
	public void testFindListEmpty() {
		
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		
		when(repository.findList(ids)).thenReturn(prodList2);
		
		assertEquals(prodList2, service.findList(ids));
	}
	
	@Test
	public void testFindListOk() {
		
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		
		when(repository.findList(ids)).thenReturn(prodList1);
		
		assertEquals(prodList1, service.findList(ids));
	}
	
	@Test
	public void testFindAllEmpty() {
		when(repository.findAll()).thenReturn(prodList1);
		
		assertEquals(prodList1, service.findAll());
	}
	
	@Test
	public void testFindAllOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		when(repository.findAll()).thenReturn(prodList1);
		
		assertEquals(prodList1, service.findAll());
	}

	@Test
	public void testEditNull() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertNull(service.edit(fullProd2, 1L));
	}
	
	@Test
	public void testEditOk() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(service.save(fullProd1)).thenReturn(fullProd1);
		Product result = service.edit(fullProd2, 1L);
		
		assertEquals(fullProd2.getName(), fullProd1.getName());
		assertEquals(fullProd2.getPrice(), fullProd1.getPrice());
		assertEquals(fullProd2.getStock(), fullProd1.getStock());
		assertEquals(fullProd2.getOnSale(), fullProd1.getOnSale());
		
		assertEquals(fullProd1, result);
	}

	@Test
	public void testEditPriceNull() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertNull(service.editPrice(99.9, 1L));
	}
	
	@Test
	public void testEditPriceOk() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(service.save(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(1.0, fullProd1.getPrice());
		
		Product res = service.editPrice(99.9, 1L);
		
		assertEquals(99.9, fullProd1.getPrice());
		
		assertEquals(fullProd1, res);
	}

	@Test
	public void testEditPriceMapEmpty() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.findById(2L)).thenReturn(Optional.of(fullProd2));
		
		assertEquals(emptyList, service.editPriceMap(idAndPricesB));
	}
	
	@Test
	public void testEditPriceMapOk() {
		prodList1.add(fullProd1);
		
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(1.0, fullProd1.getPrice());
		assertEquals(1.0, fullProd2.getPrice());
		
		List<Product> res = service.editPriceMap(idAndPricesA);
		
		assertEquals(10.0, fullProd1.getPrice());
		assertEquals(1.0, fullProd2.getPrice());
		
		assertEquals(prodList1, res);
	}

	@Test
	public void testRemoveOnSaleNull() {
		when(repository.findById(3L)).thenReturn(Optional.empty());
		
		assertNull(service.removeOnSale(3L));
	}
	
	@Test
	public void testRemoveOnSaleOk() {
		when(repository.findById(3L)).thenReturn(Optional.of(offerProd));
		when(repository.save(offerProd)).thenReturn(offerProd);
		
		assertEquals(true, offerProd.getOnSale());
		
		Product res = service.removeOnSale(3L);
		
		assertEquals(false, offerProd.getOnSale());
		
		assertEquals(offerProd, res);
	}

	@Test
	public void testRemoveOnSaleList() {
		prodList1.add(fullProd1);
		prodList1.add(offerProd);
		
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		when(repository.findById(3L)).thenReturn(Optional.of(offerProd));
		when(repository.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(false, fullProd1.getOnSale());
		assertEquals(false, fullProd2.getOnSale());
		assertEquals(true, offerProd.getOnSale());
		
		List<Product> res = service.removeOnSaleList(ids);
		
		assertEquals(false, fullProd1.getOnSale());
		assertEquals(false, fullProd2.getOnSale());
		assertEquals(false, offerProd.getOnSale());
		
		assertEquals(prodList1, res);
	}

	@Test
	public void testPutOnSaleNull() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertNull(service.putOnSale(1L));
	}
	
	@Test
	public void testPutOnSaleOk() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.save(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(false, fullProd1.getOnSale());
		
		Product res = service.putOnSale(1L);
		
		assertEquals(true, fullProd1.getOnSale());
		
		assertEquals(fullProd1, res);
	}

	@Test
	public void testPutOnSaleList() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(27L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.findById(2L)).thenReturn(Optional.of(fullProd2));
		when(repository.findById(27L)).thenReturn(Optional.empty());
		when(repository.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(false, fullProd1.getOnSale());
		assertEquals(false, fullProd2.getOnSale());
		assertEquals(true, offerProd.getOnSale());
		
		List<Product> res = service.putOnSaleList(ids);
		
		assertEquals(true, fullProd1.getOnSale());
		assertEquals(true, fullProd2.getOnSale());
		assertEquals(true, offerProd.getOnSale());
		
		assertEquals(prodList1, res);
	}

	@Test
	public void testAddStockNull() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertNull(service.addStock(10, 1L));
	}
	
	@Test
	public void testAddStockOk() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.save(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(2, fullProd1.getStock());
		
		Product res = service.addStock(10, 1L);
				
		assertEquals(12, fullProd1.getStock());
		
		assertEquals(fullProd1, res);
	}

	@Test
	public void testAddStockMapEmpty() {
		when(repository.findById(3L)).thenReturn(Optional.empty());
		when(repository.findById(4L)).thenReturn(Optional.empty());
		
		assertEquals(emptyList, service.addStockMap(idAndStocksB));
	}
	
	@Test
	public void testAddStockMapOk() {
		prodList1.add(fullProd1);
		prodList1.add(fullProd2);
		
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.findById(2L)).thenReturn(Optional.of(fullProd2));
		when(repository.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(2, fullProd1.getStock());
		assertEquals(2, fullProd2.getStock());
		
		prodList2 = service.addStockMap(idAndStocksA);
		
		assertEquals(3, fullProd1.getStock());
		assertEquals(24, fullProd2.getStock());
		
		assertEquals(prodList1, prodList2);
	}

	@Test
	public void testSellStockNull() {
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertNull(service.sellStock(1, 1L));
	}
	
	@Test
	public void testSellStockNotEnoughStock() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		
		assertNull(service.sellStock(99, 1L));
	}
	
	@Test
	public void testSellStockOk() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.save(fullProd1)).thenReturn(fullProd1);
		
		assertEquals(2, fullProd1.getStock());
		
		Product res = service.sellStock(1, 1L);
		
		assertEquals(1, fullProd1.getStock());
		
		assertEquals(fullProd1, res);
	}
	
	@Test
	public void testSellStockMapEmpty() {
		when(repository.findById(3L)).thenReturn(Optional.empty());
		when(repository.findById(4L)).thenReturn(Optional.empty());
		
		assertEquals(emptyList, service.sellStockMap(idAndStocksB));
	}
	
	@Test
	public void testSellStockMapOk() {
		prodList1.add(fullProd1);
		
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		when(repository.findById(2L)).thenReturn(Optional.of(fullProd2));
		when(repository.saveAll(prodList1)).thenReturn(prodList1);
		
		assertEquals(2, fullProd1.getStock());
		assertEquals(2, fullProd2.getStock());
		
		prodList2 = service.sellStockMap(idAndStocksA);
		
		assertEquals(1, fullProd1.getStock());
		assertEquals(2, fullProd2.getStock());
		
		assertEquals(prodList1, prodList2);
	}

	@Test
	public void testDeleteById() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		
		assertEquals(Optional.of(fullProd1), service.findById(1L));
		
		service.deleteById(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertEquals(Optional.empty(), service.findById(1L));
	}

	@Test
	public void testDeleteAll() {
		when(repository.findById(1L)).thenReturn(Optional.of(fullProd1));
		
		assertEquals(Optional.of(fullProd1), service.findById(1L));
		
		service.deleteAll();
		
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		assertEquals(Optional.empty(), service.findById(1L));
	}

}
