package com.springboot.app.ecommerce.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.ecommerce.dto.UsuarioDTO;
import com.springboot.app.ecommerce.model.Usuario;
import com.springboot.app.ecommerce.service.EcommerceService;

@Controller
@RequestMapping("/api/view/users")
public class UserJspController {

	@Autowired
	private EcommerceService service;
	
	public UserJspController(EcommerceService service) {
		this.service = service;
	}
	
	@Autowired
	private RestTemplate restClient;
	
	
	
	private Usuario clientUsuarioActual() {
		String uri = "http://localhost:8002/user";
	    Usuario user = restClient.getForObject(
				uri,
				Usuario.class);
	    return user;
	}
	
	private Boolean clientIsLoggedIn() {
		String uri = "http://localhost:8002/isLoggedIn";
	    Boolean response = restClient.getForObject(
				uri,
				Boolean.class);
	    return response;
	}
	
	private Boolean clientEsAdmin() {
		String uri = "http://localhost:8002/esAdmin";
	    Boolean response = restClient.getForObject(
				uri,
				Boolean.class);
	    return response;
	}
	
	private List<Usuario> clientFindAllUsuarios() {
		String uri = "http://localhost:8002/user/find/all";
	    ResponseEntity<Usuario[]> response = restClient.getForEntity(
				uri,
				Usuario[].class);
	    return new ArrayList<>(Arrays.asList(response.getBody()));
	}
	
	private Usuario clientFindUsuarioByUsername(String username) {
		String uri = "http://localhost:8002/user/find/{username}";
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("username", username);
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class,
				params);
	    return response.getBody();
	}
	
	private void clientLogin(String username, String password) {
		String uri = "http://localhost:8002/login";
		UsuarioDTO dto = new UsuarioDTO(username, password);
	    restClient.put(
	    		uri,
	    		dto);
	}
	
	private void clientLogout() {
		String uri = "http://localhost:8002/logout";
	    restClient.put(
	    		uri,
	    		null);
	}
	
	private Boolean clientRegister(String username, String password) {
		String uri = "http://localhost:8002/register";
		UsuarioDTO dto = new UsuarioDTO(username, password);
	    return restClient.postForEntity(
				uri,
				dto,
				Boolean.class).getBody();
	}
	
	
	
	
	
	@GetMapping("/index")
	public String userIndex(Model model) {
		model.addAttribute("isLoggedIn", clientIsLoggedIn());
		if(!clientIsLoggedIn()) {
			model.addAttribute("username", "Guest");
		} else {
			Usuario user = clientUsuarioActual();
			model.addAttribute("username", user.getUsername());
			model.addAttribute("esAdmin", clientEsAdmin());
		}
		
		return "user/user-index";
	}
	
	@GetMapping("/all")
	public String viewUsers(Model model) {
		
		if(clientIsLoggedIn()) {
			
			if(clientEsAdmin()) {
			
				List<Usuario> users = clientFindAllUsuarios();
				if(users.size() > 0) {
					model.addAttribute("users", users);
					return "user/users-list";
				}
				return "error/no-users-error";
				
			}
			return "error/forbidden-error";
			
		}
		
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("username") String username,
						@ModelAttribute("password") String password,
						Model model) {
		if(clientIsLoggedIn()) {
			return "error/already-loged-in-error";
		}
		clientLogin(username, password);
		if(clientIsLoggedIn()) {
			model.addAttribute("username", username);
			return "user/loged-in";
		}
		return "error/wrong-username-or-password-error";
	}
	
	@PostMapping("/logout")
	public String logout() {
		if(!clientIsLoggedIn()) {
			return "error/already-loged-out-error";
		}
		
		clientLogout();
		
		return "user/loged-out";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute("username") String username,
							@ModelAttribute("password") String password,
							Model model) {
		
		if(clientIsLoggedIn()) {
			return "error/already-loged-in-error";
		}
		
		Usuario user = clientFindUsuarioByUsername(username);
		if(user != null) {
			return "error/name-already-exists-error";
		}

		clientRegister(username, password);
		
		model.addAttribute("username", username);
		return "user/loged-in";
	}
	
	
	
	
}
