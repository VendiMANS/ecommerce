package com.springboot.app.users.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.app.users.dto.UsuarioDTO;
import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Usuario;
import com.springboot.app.users.service.UserService;

//@Controller
//@RequestMapping("/api/view/users")
public class JspController {
/*
	@Autowired
	private UserService service;
	
	public JspController(UserService service) {
		this.service = service;
	}
	
	
	
	
	
	@GetMapping("/index")
	public String saleIndex(Model model) {
		model.addAttribute("isLoggedIn", service.isLoggedIn());
		if(!service.isLoggedIn()) {
			model.addAttribute("username", "Guest");
		} else {
			model.addAttribute("username", service.getUsuarioActual().getUsername());
			model.addAttribute("esAdmin", service.esAdmin());
		}
		
		return "user-index";
	}
	
	@GetMapping("/all")
	public String viewUsers(Model model) {
		
		if(service.isLoggedIn()) {
			
			if(service.esAdmin()) {
			
				List<Usuario> users = service.findAllUsuarios();
				if(users.size() > 0) {
					model.addAttribute("users", users);
					return "users-list";
				}
				return "no-users-error";
				
			}
			return "forbidden-error";
			
		}
		
		return "loged-out-error";
		
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("username") String username,
						@ModelAttribute("password") String password,
						Model model) {
		if(service.isLoggedIn()) {
			return "already-loged-in";
		}
		
		Usuario usuarioInDB = service.findUsuarioByUsername(username);
		if(usuarioInDB != null && usuarioInDB.getPassword().equals(password)) {
			Boolean login = service.login(usuarioInDB);
			if(login) {
				model.addAttribute("username", username);
				return "loged-in";
			}
		}
		return "wrong-username-or-password";
	}
	
	@PostMapping("/logout")
	public String logout() {
		if(!service.isLoggedIn()) {
			return "already-loged-out";
		}
		
		service.logout();
		
		return "loged-out";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("username") String username,
							@ModelAttribute("password") String password,
							Model model) {
		
		if(service.isLoggedIn()) {
			return "already-loged-in";
		}
		
		Usuario usuarioInDB = service.findUsuarioByUsername(username);
		if(usuarioInDB == null) {
			Usuario usuario;
			PermisosUsuario permiso;
			if(service.countUsuarios() == 0L) {
				permiso = service.findPermisoByName("admin");
	    		if(permiso == null) {
	    			model.addAttribute("name", "admin");
	    			return "permiso-not-found";
	    		}
			} else {
				permiso = service.findPermisoByName("cliente");
	    		if(permiso == null) {
	    			model.addAttribute("name", "cliente");
	    			return "permiso-not-found";
	    		}
			}
			usuario = new Usuario(username, password, permiso);
			service.saveUsuario(usuario);
			
			service.login(usuario);
			
			model.addAttribute("username", username);
			return "loged-in";
		}
		return "name-already-exists";
	}
	
	
	
	
	
	
*/	
}
