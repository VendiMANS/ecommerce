package com.springboot.app.users.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.app.users.dto.UsuarioDTO;
import com.springboot.app.users.model.Usuario;
import com.springboot.app.users.service.UserService;

@RestController
public class UserController {
    
	@Autowired
    private UserService service;

    
	
	@GetMapping("/user")
	public ResponseEntity<Usuario> user(){
		Usuario user = service.getUsuarioActual();
		if(user != null) {
			return new ResponseEntity<Usuario>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Usuario>(user, HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<Boolean> registerCliente(@RequestBody UsuarioDTO dto){
		if(!service.isLoggedIn()) {
			Usuario usuarioInDB = service.findUsuarioByUsername(dto.getUsername());
			if(usuarioInDB == null) {
				Usuario usuario;
				
				if(service.countUsuarios() == 0L) {
					usuario = new Usuario(dto, "admin");
				} else {
					usuario = new Usuario(dto, "cliente");
				}
				
				service.saveUsuario(usuario);
				
				login(dto);
				
				return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
			}
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/register/admin")
	public ResponseEntity<Boolean> registerAdmin(@RequestBody UsuarioDTO dto){
		if(service.esAdmin()) {	
			Usuario usuarioInDB = service.findUsuarioByUsername(dto.getUsername());
			if(usuarioInDB == null) {
				Usuario usuario = new Usuario(dto, "admin");
				service.saveUsuario(usuario);
				return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
			}
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
	}
	
	@GetMapping("/user/find/all")
	public ResponseEntity<List<Usuario>> findAllUsuarios(){
		if(service.esAdmin()) {	
			return new ResponseEntity<List<Usuario>>(service.findAllUsuarios(), HttpStatus.OK);
		}
		return new ResponseEntity<List<Usuario>>(Collections.emptyList(), HttpStatus.FORBIDDEN);
	}
	
	
	
	
	
}