package com.springboot.app.users.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.app.users.dto.UsuarioDTO;
import com.springboot.app.users.model.Cart;
import com.springboot.app.users.model.CartItem;
import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Usuario;
import com.springboot.app.users.service.UserService;

@RestController
public class UserController {
    
	@Autowired
    private UserService service;

    
	
	@GetMapping("/user")
	public Usuario user(){
		return service.getUsuarioActual();
	}
	
	@GetMapping("/isLoggedIn")
	public Boolean isLoggedIn() {
		return service.isLoggedIn();
	}
	
	@GetMapping("/esAdmin")
	public Boolean esAdmin() {
		return service.esAdmin();
	}
	
	@PutMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody UsuarioDTO dto){
		Usuario usuarioInDB = service.findUsuarioByUsername(dto.getUsername());
		if(usuarioInDB != null && usuarioInDB.getPassword().equals(usuarioInDB.getPassword())) {
			Boolean login = service.login(usuarioInDB);
			if(login) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/logout")
	public ResponseEntity<Boolean> logout(){
		Boolean logout = service.logout();
		if(logout) {
			return new ResponseEntity<Boolean>(logout, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(logout, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@RequestBody UsuarioDTO dto){
		if(!service.isLoggedIn()) {
			Usuario usuarioInDB = service.findUsuarioByUsername(dto.getUsername());
			if(usuarioInDB == null) {
				Usuario usuario;
				PermisosUsuario permiso;
				if(service.countUsuarios() == 0L) {
					
					permiso = service.findPermisoByName("admin");
		    		if(permiso == null) {
		    			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		    		}
										
				} else {

					permiso = service.findPermisoByName("cliente");
		    		if(permiso == null) {
		    			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		    		}
					
				}
				
				usuario = new Usuario(dto, permiso);
				service.saveUsuario(usuario);
				
				login(dto);
				
				return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/user/find/all")
	public ResponseEntity<List<Usuario>> findAllUsuarios(){
		if(service.esAdmin()) {	
			return new ResponseEntity<List<Usuario>>(service.findAllUsuarios(), HttpStatus.OK);
		}
		return new ResponseEntity<List<Usuario>>(Collections.emptyList(), HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/user/find/{username}")
	public ResponseEntity<Usuario> findUsuarioByUsername(@PathVariable String username){
		Usuario user = service.findUsuarioByUsername(username);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@GetMapping("/user/cart")
	public Cart getCurrentCart() {
		return service.getCurrentCart();
	}
	
	
	
	
	
	@PutMapping("/user/cart/add/item")
	public void cartAddItem(@RequestBody CartItem item) {
		service.addItem(item);
	}
	
	@PutMapping("/user/cart/remove/item")
	public void cartRemoveItem(@RequestBody CartItem item) {
		service.removeItem(item);
	}
	
	@PutMapping("/user/cart/add/amount/{prodId}")
	public void cartAddAmount(@PathVariable Long prodId, @RequestBody Integer amount) {
		service.addAmount(prodId,amount);
	}
	
	@PutMapping("/user/cart/remove/amount/{prodId}")
	public void cartRemoveAmount(@PathVariable Long prodId, @RequestBody Integer amount) {
		service.removeAmount(prodId,amount);
	}
	
	
	
	@GetMapping("/user/item/{prodId}")
	public CartItem findCartItemByProdId(@PathVariable Long prodId) {
		return service.findItemByProdId(prodId);
	}
	
	
	
	
	
	
	
	@GetMapping("/user/cart/isEmpty")
	public Boolean isEmpty() {
		return service.cartIsEmpty();
	}
	
	@GetMapping("/user/cart/prodIsInCart/{id}")
	public Boolean prodIsInCart(@PathVariable Long id) {
		return service.prodIsInCart(id);
	}
	
	@PostMapping("/user/cart/purchase")
	public List<CartItem> cartPurchase(){
		return service.purchaseCart();
	}
	
	@PutMapping("/user/cart/clear")
	public void cartClear() {
		service.clearCart();
	}
}