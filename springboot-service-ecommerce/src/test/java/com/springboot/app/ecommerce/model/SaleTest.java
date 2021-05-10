package com.springboot.app.ecommerce.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaleTest {

	private Sale emptyObj;
	private Sale fullObj1;
	private Sale fullObj2;
	
	@BeforeEach
	public void setup() {
		emptyObj = new Sale();
		
		fullObj1 = new Sale("Product A", 1);
		fullObj1.setId(1L);
		
		fullObj2 = new Sale("Product B", 2);
		fullObj2.setId(2L);
	}
	
	

	@Test
	void testGetId() {
		assertEquals(1L, fullObj1.getId());
		assertEquals(2L, fullObj2.getId());
	}

	@Test
	void testSetId() {
		assertEquals(1L, fullObj1.getId());
		
		fullObj1.setId(55L);
		
		assertEquals(55L, fullObj1.getId());
	}

	@Test
	void testGetProductName() {
		assertEquals("Product A", fullObj1.getProductName());
		assertEquals("Product B", fullObj2.getProductName());
	}

	@Test
	void testSetProductName() {
		assertEquals("Product A", fullObj1.getProductName());
		
		fullObj1.setProductName("Another prod");
		
		assertEquals("Another prod", fullObj1.getProductName());
	}

	@Test
	void testDate() {
		Date date = new Date();
		fullObj1.setDate(date);
		
		assertEquals(date, fullObj1.getDate());
	}
	
	@Test
	void testGetAmount() {
		assertEquals(1, fullObj1.getAmount());
		assertEquals(2, fullObj2.getAmount());
	}

	@Test
	void testSetAmount() {
		assertEquals(1, fullObj1.getAmount());
		
		fullObj1.setAmount(99);
		
		assertEquals(99, fullObj1.getAmount());
	}
	
	@Test
	void testHash() {
		assertNotEquals(emptyObj.hashCode(), fullObj1.hashCode());
	}
	
	@Test
	void testEquals() {
		
		Date date1 = new Date(1111);
		Date date2 = new Date(8888);
		
		assertEquals(emptyObj,emptyObj);
        assertNotEquals(emptyObj,null);
        assertNotEquals(emptyObj,27);
		
        assertNotEquals(emptyObj,fullObj1);
		fullObj1.setAmount(null);
		assertNotEquals(emptyObj,fullObj1);
		fullObj1.setAmount(2);
		emptyObj.setAmount(22);
		assertNotEquals(emptyObj,fullObj1);
        
		emptyObj.setAmount(2);
		assertNotEquals(emptyObj,fullObj1);
		fullObj1.setDate(null);
		assertNotEquals(emptyObj,fullObj1);
		fullObj1.setDate(date1);
		emptyObj.setDate(date2);
		assertNotEquals(emptyObj,fullObj1);
		
		emptyObj.setDate(date1);
		assertNotEquals(emptyObj,fullObj1);
		fullObj1.setId(null);
		assertNotEquals(emptyObj,fullObj1);
		fullObj1.setId(1L);
		emptyObj.setId(2L);
		assertNotEquals(emptyObj,fullObj1);
		
		emptyObj.setId(1L);
		assertNotEquals(emptyObj,fullObj1);
		fullObj1.setProductName(null);
		assertEquals(emptyObj,fullObj1);
		fullObj1.setProductName("A");
		emptyObj.setProductName("B");
		assertNotEquals(emptyObj,fullObj1);
		
		emptyObj.setProductName("A");
		assertEquals(emptyObj,fullObj1);
		
	}
	
	@Test
	void testToString() {
		Date date = new Date(1111);
		
		fullObj2.setDate(date);
		
		String str = "Sale [" +
				"id=" + 2L +
				", productName=" + "Product B" +
				", date=" + date +
				", amount=" + 2 +
				"]";
		
		assertEquals(str, fullObj2.toString());
	}

}
