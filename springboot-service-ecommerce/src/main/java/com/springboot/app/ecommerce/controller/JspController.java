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
import com.springboot.app.ecommerce.model.Sale;
import com.springboot.app.ecommerce.model.Usuario;
import com.springboot.app.ecommerce.service.EcommerceService;

@Controller
@RequestMapping("/api/view")
public class JspController {

	@Autowired
	private EcommerceService service;
	
	public JspController(EcommerceService service) {
		this.service = service;
	}
	
	@Autowired
	private RestTemplate restClient;
	
	
	
	private Usuario usuarioActual() {
		String uri = "http://localhost:8002/user";
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class);
	    return response.getBody();
	}
	
	
	
	@GetMapping("/sale/index")
	public String saleIndex() {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {
	    		return "sales/sales-index";
	    	}
	    	return "forbidden-error";
	    }
	    
	    return "loged-out-error";
		
	}
	
	@GetMapping("/sale/allSales")
	public String viewSales(Model model) {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		List<Sale> sales = service.findAll();
	    		if(sales.size() > 0) {
	    			model.addAttribute("sales", sales);
	    			return "sales/sale-list";
	    		}
	    		return "sales/no-sales-error";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	@GetMapping("/sale/allSalesByMonth")
	public String viewSalesByMonth(@ModelAttribute("month") Integer month, Model model) {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		List<Sale> sales = service.findAllByMonth(month);
	    		if(sales.size() > 0) {
	    			model.addAttribute("sales", sales);
	    			return "sales/sale-list";
	    		}
	    		model.addAttribute("month", month);
	    		return "sales/no-sales-in-month-error";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
	}
	
	@GetMapping("/sale/saleById")
	public String viewSaleById(@ModelAttribute("id") Long id, Model model) {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {
	    		
	    		Optional<Sale> search = service.findById(id);
	    		if(search.isPresent()) {
	    			Sale sale = search.get();
	    			model.addAttribute("sale", sale);
	    			return "sales/sale-by-id";
	    		}
	    		return "sales/sale-by-id-error";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	@GetMapping("/sale/deleteById")
	public String deleteSaleById(@ModelAttribute("id") Long id, Model model) {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {
	    		
	    		Optional<Sale> search = service.findById(id);
	    		if(search.isPresent()) {
	    			model.addAttribute("id", id);
	    			service.deleteById(id);
	    			return "sales/delete-by-id";
	    		}
	    		return "sales/sale-by-id-error";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
		
	}
	
	@GetMapping("/sale/deleteAll")
	public String deleteAllSales() {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {
	    		
	    		service.deleteAll();
	    		return "sales/delete-all-sales";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
		
	}
	
	
	
	
	
	@GetMapping("/cart/index")
	public String cartIndex() {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
			
			return "cart/cart-index";
			
	    }
		return "loged-out-error";
		
	}
	
	@GetMapping("/cart/get")
	public String viewCart(Model model) {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {

			List<CartItem> items = service.getCart();
			if(items != null) {
				model.addAttribute("items", items);
				return "cart/get";
			}
			return "cart/empty-cart-error";
			
	    }
		return "loged-out-error";
		
	}
	
	@PostMapping("/cart/purchase")
	public String cartPurchase(Model model) {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {

			if(!service.cartIsEmpty()) {
		    	List<CartItem> items = service.purchaseCart();
		    	model.addAttribute("items", items);
		    	return "cart/cart-purchased";
		    }
		    return "cart/empty-cart-error";
			
	    }
		return "loged-out-error";
		
	}
	
	@PostMapping("/cart/clear")
	public String cartClear() {
		
		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {

			if(!service.cartIsEmpty()) {
		    	service.clearCart();
		    	return "cart/cart-cleared";
		    }
		    return "cart/empty-cart-error";
			
	    }
		return "loged-out-error";
		
	}
	
	
	
	
	
}
