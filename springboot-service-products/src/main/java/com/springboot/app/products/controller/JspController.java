package com.springboot.app.products.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.products.model.Usuario;
import com.springboot.app.products.model.Product;
import com.springboot.app.products.service.ProductService;

@Controller
@RequestMapping("/api/view/product")
public class JspController {

	@Autowired
	private RestTemplate restClient;
	
	@Autowired
	private ProductService service;
	
	public JspController(ProductService service) {
		this.service = service;
	}
	
	
	
	private Usuario usuarioActual() {
		String uri = "http://localhost:8002/user";
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class);
	    return response.getBody();
	}
	
	
	
	
	
	
	@GetMapping("/index")
	public String prodIndex() {
		
		return "prod-index";
	}
	
	@GetMapping("/all")
	public String viewProds(Model model) {
		
		List<Product> prods = service.findAll();
		if(prods.size() > 0) {
			model.addAttribute("prods", prods);
			return "prod-list";
		}
		return "no-prods-error";
	}
	
	@GetMapping("/onSale")
	public String viewProdsOnSale(Model model) {
		
		List<Product> prods = service.findOffers();
		if(prods.size() > 0) {
			model.addAttribute("prods", prods);
			return "prod-list";
		}
		return "no-prods-error";
	}
	
	@GetMapping("/byId")
	public String viewProdById(@ModelAttribute("id") Long id, Model model) {
		
		Optional<Product> search = service.findById(id);
		if(search.isPresent()) {
			Product prod = search.get();
			model.addAttribute("prod", prod);
			return "prod-by-id";
		}
		return "prod-by-id-not-found";
	}
	
	
	
	
	
	@PostMapping("/save")
	public String saveProd(@ModelAttribute("name") String name,
						@ModelAttribute("price") Double price,
						@ModelAttribute("stock") Integer stock,
						@ModelAttribute("onSale") Boolean onSale,
						Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		Optional<Product> productSearch = service.findByName(name);
	    		if(!productSearch.isPresent()) {
	    			if(name != null && price != null && stock != null && onSale != null) {
	    				Product prod = new Product(name, price, stock, onSale);
	    				service.save(prod);
	    				model.addAttribute("prod", prod);
	    				return "save-prod-ok";
	    			}
	    			return "save-prod-null-field";
	    		}
	    		model.addAttribute(name);
	    		return "save-prod-already-exists";
	    		
	    	}
	    	return "forbidden-error";
	    }
	    return "loged-out-error";
		
	}
	
	@PostMapping("/edit/price")
	public String editProdPrice(@ModelAttribute("price") Double price,
						@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		Product prod = service.editPrice(price,id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "edit-prod-ok";
	    		}
	    		return "prod-by-id-not-found";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	@PostMapping("/edit/onSale/put")
	public String editProdPutOnSale(@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		Product prod = service.putOnSale(id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "edit-prod-ok";
	    		}
	    		return "prod-by-id-not-found";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	@PostMapping("/edit/onSale/remove")
	public String editProdRemoveOnSale(@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {
	    		
	    		Product prod = service.removeOnSale(id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "edit-prod-ok";
	    		}
	    		return "prod-by-id-not-found";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	@PostMapping("/edit/stock/add")
	public String editProdStockAdd(@ModelAttribute("stock") Integer stock,
						@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		Product prod = service.addStock(stock, id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "edit-prod-ok";
	    		}
	    		return "prod-by-id-not-found";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
		
	
	
	@GetMapping("/delete/byId")
	public String deleteProdById(@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		Optional<Product> search = service.findById(id);
	    		if(search.isPresent()) {
	    			service.deleteById(id);
	    			model.addAttribute("id", id);
	    			return "delete-by-id";
	    		}
	    		return "prod-by-id-not-found";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	@GetMapping("/delete/all")
	public String deleteAllProds() {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		service.deleteAll();
	    		return "delete-all-prods";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	
	
	@GetMapping("/resetIdCounter")
	public String resetIdCounter() {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.getPermiso().equals("admin")) {

	    		if(service.findAll().size() == 0) {
	    			service.resetIdCounter();
	    			return "reset-id-counter-ok";
	    		}
	    		return "reset-id-counter-error";
	    		
	    	}
	    	return "forbidden-error";
	    }
		return "loged-out-error";
		
	}
	
	
	
	
	@PostMapping("/cart/add")
	public String cartAdd(@ModelAttribute("id") Long id,
						@ModelAttribute("name") String name,
						@ModelAttribute("amount") Integer amount, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {

			//consumo el metodo PUT addProdToCartPOST(Long, Integer) del controlador de ecommerce
			
			String uri = "http://localhost:8090/api/cart/add/{id}"; 
			
			Map<String, Object> params = new HashMap<String, Object>();
		    params.put("id", id);
		    
			
		    
			if(amount > 0) {
				Product prod = service.findById(id).get();
				
				model.addAttribute("name", name);
				model.addAttribute("amount", amount);
				if(prod.hasEnoughStockToSell(amount)) {
					

				    restClient.put(
				    		uri,
				    		amount,
				    		params);
					
					
					return "cart-add";
				} else {
					return "not-enough-stock";
				}
			}
			return "invalid-amount";
			
	    }
		return "loged-out-error";
		
	}
	
	@PostMapping("/cart/remove")
	public String cartRemove(@ModelAttribute("id") Long id,
						@ModelAttribute("name") String name,
						@ModelAttribute("amount") Integer amount, Model model) {

		Usuario usuarioActual = usuarioActual();
		if(usuarioActual != null) {

			//consumo el metodo PUT removeProdToCartPOST(Long, Integer) del controlador de ecommerce
			
			String uriPUT = "http://localhost:8090/api/cart/remove/{id}"; 
			
			Map<String, Object> paramsPUT = new HashMap<String, Object>();
		    paramsPUT.put("id", id);
		    
			
		    
		    //consumo el GET para ver si esta vacio el carrito
		    String uriGETcart = "http://localhost:8090/api/cart/list";
			
		    ResponseEntity<Object> responseCart = restClient.getForEntity(
					uriGETcart,
					Object.class);
		    
		    
		    if(responseCart.getStatusCode() != HttpStatus.BAD_REQUEST) {
		    	
		    	
		    	if(amount > 0) {
					
					
		    		//////////////////////////////////
					//consumo el GET para ver si el prod esta en el carrito
					String uriGETprodInCart = "http://localhost:8090/api/cart/prodIsInCart/{id}";
					
					Map<String, Object> paramsGET = new HashMap<String, Object>();
				    paramsGET.put("id", id);
					
				    ResponseEntity<Boolean> responseProd = restClient.getForEntity(
							uriGETprodInCart,
							Boolean.class,
							paramsGET);
				    //////////////////////////////////
					
				    model.addAttribute("name", name);
					model.addAttribute("amount", amount);
					if(responseProd.getBody()) {
						

					    restClient.put(
					    		uriPUT,
					    		amount,
					    		paramsPUT);
					    
						return "cart-remove";
					} else {
						return "prod-not-in-cart";
					}
				}
				return "invalid-amount";
				
				
		    }
		    return "empty-cart-error";
			
	    }
		return "loged-out-error";
		
	}
	
	
	
	
	
}
