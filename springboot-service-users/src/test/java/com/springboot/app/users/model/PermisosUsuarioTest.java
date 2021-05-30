package com.springboot.app.users.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PermisosUsuarioTest {


	
	PermisosUsuario vacio;
	PermisosUsuario noVacio;
	
	@BeforeEach
	public void setup() {
		
		vacio = new PermisosUsuario();
		noVacio = new PermisosUsuario("usuario comun");
		noVacio.setId(1L);
	}
	
	
	
	@Test
	void testGetId() {
		assertNull(vacio.getId());
	}

	@Test
	void testSetId() {
		assertEquals(1L, noVacio.getId());
		
		noVacio.setId(2L);
		
		assertEquals(2L, noVacio.getId());
	}

	@Test
	void testGetName() {
		assertNull(vacio.getName());
	}

	@Test
	void testSetName() {
		assertEquals("usuario comun", noVacio.getName());
		
		noVacio.setName("usuario admin");
		
		assertEquals("usuario admin", noVacio.getName());
	}

	

}
