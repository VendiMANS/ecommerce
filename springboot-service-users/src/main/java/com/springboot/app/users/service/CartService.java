package com.springboot.app.users.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.users.model.Cart;
import com.springboot.app.users.model.CartItem;
import com.springboot.app.users.model.Product;
import com.springboot.app.users.model.Sale;
import com.springboot.app.users.model.Usuario;
import com.springboot.app.users.repository.ICartRepository;

@Service
public class CartService implements ICartService {

	@Autowired
	private RestTemplate restClient;
	
	@Autowired
	private ICartRepository repository;
	
	
	
	private Usuario clientUsuarioActual() {
		String uri = "http://localhost:8002/user";
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class);
	    return response.getBody();
	}
	
	private Boolean clientIsLoggedIn() {
		String uri = "http://localhost:8002/isLoggedIn";
	    Boolean response = restClient.getForObject(
				uri,
				Boolean.class);
	    return response;
	}
	
	private Cart clientCurrentCart() {
		String uri = "http://localhost:8002/user/cart";
	    Cart cart = restClient.getForObject(
				uri,
				Cart.class);
	    return cart;
	}
	
	private Product clientProdFindById(Long id) {
		String uri = "http://localhost:8001/find/id/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    return restClient.getForObject(
				uri,
				Product.class,
				params);
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	
}
