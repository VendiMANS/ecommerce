package com.springboot.app.ecommerce.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.ecommerce.model.Cart;
import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Usuario;

@Controller
@RequestMapping("/api/view/cart")
public class CartJspController {

	@Autowired
	private RestTemplate restClient;
	
	
	
	private Usuario clientUsuarioActual() {
		String uri = "http://localhost:8002/user";
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class);
	    return response.getBody();
	}
	
	private Cart clientCurrentCart() {
		String uri = "http://localhost:8002/user/cart";
	    return restClient.getForObject(
				uri,
				Cart.class);
	}
	
	private Boolean clientCartIsEmpty() {
		String uri = "http://localhost:8002/user/cart/isEmpty";
	    Boolean res = restClient.getForObject(
				uri,
				Boolean.class);
	    return res;
	}
	
	private List<CartItem> clientCartPurchase() {
		String uri = "http://localhost:8002/user/cart/purchase";
	    CartItem[] itemArr = restClient.postForObject(
	    		uri,
	    		null,
	    		CartItem[].class);
	    return Arrays.asList(itemArr);
	}
	
	private void clientCartClear() {
		String uri = "http://localhost:8002/user/cart/clear";
	    restClient.put(
	    		uri,
	    		null);
	}
	
	
	
	
	
	@GetMapping("/index")
	public String cartIndex() {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			
			return "cart/cart-index";
			
	    }
		return "error/loged-out-error";
		
	}
	
	@GetMapping("/get")
	public String viewCart(Model model) {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {

			Set<CartItem> items = clientCurrentCart().getItems();
			if(items.size() > 0) {
				model.addAttribute("items", items);
				return "cart/get";
			}
			return "error/empty-cart-error";
			
	    }
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/purchase")
	public String cartPurchase(Model model) {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {

			if(!clientCartIsEmpty()) {
		    	List<CartItem> items = clientCartPurchase();
		    	model.addAttribute("items", items);
		    	return "cart/cart-purchased";
		    }
		    return "error/empty-cart-error";
			
	    }
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/clear")
	public String cartClear() {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {

			if(!clientCartIsEmpty()) {
		    	clientCartClear();
		    	return "cart/cart-cleared";
		    }
		    return "error/empty-cart-error";
			
	    }
		return "error/loged-out-error";
		
	}
	
	
	
	
	
}
