package com.springboot.app.ecommerce.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductTest {

	@Test
	void testGetId() {
		Product fullObj = new Product("A", 1.0, 2, false);
		
		assertEquals(null, fullObj.getId());
	}

	@Test
	void testSetId() {
		Product emptyObj = new Product();
		
		assertEquals(null, emptyObj.getId());
		
		emptyObj.setId(1L);
		
		assertEquals(1L, emptyObj.getId());
	}

	@Test
	void testGetName() {
		Product fullObj = new Product("A", 1.0, 2, false);
		
		assertEquals("A", fullObj.getName());
	}

	@Test
	void testSetName() {
		Product emptyObj = new Product();
		
		assertEquals(null, emptyObj.getName());
		
		emptyObj.setName("BBB");
		
		assertEquals("BBB", emptyObj.getName());
	}

	@Test
	void testGetPrice() {
		Product fullObj = new Product("A", 1.0, 2, false);
		
		assertEquals(1.0, fullObj.getPrice());
	}

	@Test
	void testSetPrice() {
		Product emptyObj = new Product();
		
		assertEquals(null, emptyObj.getPrice());
		
		emptyObj.setPrice(66.0);
		
		assertEquals(66.0, emptyObj.getPrice());
	}

	@Test
	void testGetStock() {
		Product fullObj = new Product("A", 1.0, 2, false);
		
		assertEquals(2, fullObj.getStock());
	}

	@Test
	void testSetStock() {
		Product emptyObj = new Product();
		
		assertEquals(null, emptyObj.getStock());
		
		emptyObj.setStock(5);
		
		assertEquals(5, emptyObj.getStock());
	}

	@Test
	void testGetOnSale() {
		Product fullObj = new Product("A", 1.0, 2, false);
		
		assertEquals(false, fullObj.getOnSale());
	}

	@Test
	void testSetOnSale() {
		Product emptyObj = new Product();
		
		assertEquals(null, emptyObj.getOnSale());
		
		emptyObj.setOnSale(true);
		
		assertEquals(true, emptyObj.getOnSale());
	}

	@Test
	void testHasEnoughStockToSell() {
		Product fullObj = new Product("A", 1.0, 2, false);
		
		assertFalse(fullObj.hasEnoughStockToSell(50));
		
		fullObj.setStock(51);
		
		assertTrue(fullObj.hasEnoughStockToSell(50));
	}

	@Test
	void testEqualsObject() {
		Product emptyObj = new Product();
		Product fullObj = new Product("A", 1.0, 2, false);
		fullObj.setId(1L);

        assertEquals(emptyObj,emptyObj);	// mismos objs.
        assertNotEquals(emptyObj,null);		// el segundo null.
        assertNotEquals(emptyObj,27);		// el segundo de otro tipo.
        
		assertNotEquals(emptyObj,fullObj);	// primero con param null, el otro no.
		fullObj.setId(null);
		assertNotEquals(emptyObj,fullObj);	// los dos param null.
		fullObj.setId(1L);
		emptyObj.setId(2L);
		assertNotEquals(emptyObj,fullObj);	// los dos no null pero distintos.
		
		emptyObj.setId(1L);
		assertNotEquals(emptyObj,fullObj);	// los param anteriores iguales, ahora empiezo de cero con el otro param, asi sucesivamente.
		fullObj.setName(null);
		assertNotEquals(emptyObj,fullObj);
		fullObj.setName("A");
		emptyObj.setName("B");
		assertNotEquals(emptyObj,fullObj);
		
		emptyObj.setName("A");
		assertNotEquals(emptyObj,fullObj);
		fullObj.setOnSale(null);
		assertNotEquals(emptyObj,fullObj);
		fullObj.setOnSale(false);
		emptyObj.setOnSale(true);
		assertNotEquals(emptyObj,fullObj);
		
		emptyObj.setOnSale(false);
		assertNotEquals(emptyObj,fullObj);
		fullObj.setPrice(null);
		assertNotEquals(emptyObj,fullObj);
		fullObj.setPrice(1.0);
		emptyObj.setPrice(1.1);
		assertNotEquals(emptyObj,fullObj);
		
		emptyObj.setPrice(1.0);
		assertNotEquals(emptyObj,fullObj);
		fullObj.setStock(null);
		assertEquals(emptyObj,fullObj);		// para el ultimo param cuando los dos sean null de hecho ya dan equals
		fullObj.setStock(2);
		emptyObj.setStock(22);
		assertNotEquals(emptyObj,fullObj);
		
		emptyObj.setStock(2);
		assertEquals(emptyObj,fullObj);
		
		
        
	}

	@Test
	void testHashCode() {
		Product emptyObj = new Product();
		Product fullObj = new Product("A", 1.0, 2, false);
		fullObj.setId(1L);
		
        assertNotEquals(emptyObj.hashCode(),fullObj.hashCode());
	}
	
	@Test
	void testToString() {
		Product fullObj = new Product("A", 1.0, 2, false);
		fullObj.setId(1L);
		
		String str = "Product ["
				+ "id=" + 1 +
				", name=" + "A" +
				", price=" + 1.0 +
				", stock=" + 2 +
				", onSale=" + false
				+ "]";
		
		assertEquals(str, fullObj.toString());
	}

}
