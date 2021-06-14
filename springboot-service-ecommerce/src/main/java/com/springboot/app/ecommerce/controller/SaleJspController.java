package com.springboot.app.ecommerce.controller;

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
import org.springframework.web.client.RestTemplate;

import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.PermisosUsuario;
import com.springboot.app.ecommerce.model.Sale;
import com.springboot.app.ecommerce.model.Usuario;
import com.springboot.app.ecommerce.service.EcommerceService;

@Controller
@RequestMapping("/api/view/sale")
public class SaleJspController {

	@Autowired
	private EcommerceService service;
	
	public SaleJspController(EcommerceService service) {
		this.service = service;
	}
	
	@Autowired
	private RestTemplate restClient;
	
	
	
	private Usuario clientUsuarioActual() {
		String uri = "http://localhost:8002/user";
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class);
	    return response.getBody();
	}
	
	private PermisosUsuario clientFindPermisoByName(String name) {
		String uri = "http://localhost:8002/user/find/permiso/name";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("name", name);
		ResponseEntity<PermisosUsuario> response = restClient.getForEntity(
				uri,
				PermisosUsuario.class,
				params);
		return response.getBody();
	}
	
	
	
	@GetMapping("/index")
	public String saleIndex() {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {
	    		
	    		return "sales/sales-index";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
	    
	    return "error/loged-out-error";
		
	}
	
	@GetMapping("/allSales")
	public String viewSales(Model model) {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			if(usuarioActual.tienePermiso("admin")) {

	    		List<Sale> sales = service.findAll();
	    		if(sales.size() > 0) {
	    			model.addAttribute("sales", sales);
	    			return "sales/sale-list";
	    		}
	    		return "error/no-sales-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@GetMapping("/allSalesByMonth")
	public String viewSalesByMonth(@ModelAttribute("month") Integer month, Model model) {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			if(usuarioActual.tienePermiso("admin")) {

	    		List<Sale> sales = service.findAllByMonth(month);
	    		if(sales.size() > 0) {
	    			model.addAttribute("sales", sales);
	    			return "sales/sale-list";
	    		}
	    		model.addAttribute("month", month);
	    		return "error/no-sales-in-month-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
	}
	
	@GetMapping("/saleById")
	public String viewSaleById(@ModelAttribute("id") Long id, Model model) {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			if(usuarioActual.tienePermiso("admin")) {
	    		
	    		Optional<Sale> search = service.findById(id);
	    		if(search.isPresent()) {
	    			Sale sale = search.get();
	    			model.addAttribute("sale", sale);
	    			return "sales/sale-by-id";
	    		}
	    		return "error/sale-by-id-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@GetMapping("/deleteById")
	public String deleteSaleById(@ModelAttribute("id") Long id, Model model) {
		
		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			if(usuarioActual.tienePermiso("admin")) {
	    		
	    		Optional<Sale> search = service.findById(id);
	    		if(search.isPresent()) {
	    			model.addAttribute("id", id);
	    			service.deleteById(id);
	    			return "sales/delete-by-id";
	    		}
	    		return "error/sale-by-id-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
		
	}
	
	@GetMapping("/deleteAll")
	public String deleteAllSales() {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			if(usuarioActual.tienePermiso("admin")) {
	    		
	    		service.deleteAll();
	    		return "sales/delete-all-sales";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
		
	}
	
		
	
}
