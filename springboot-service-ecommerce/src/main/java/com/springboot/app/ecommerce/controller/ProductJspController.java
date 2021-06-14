package com.springboot.app.ecommerce.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.springboot.app.ecommerce.model.Usuario;
import com.springboot.app.ecommerce.service.EcommerceService;
import com.springboot.app.ecommerce.model.Cart;
import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Product;

@Controller
@RequestMapping("/api/view/product")
public class ProductJspController {

	@Autowired
	private EcommerceService service;
	
	public ProductJspController(EcommerceService service) {
		this.service = service;
	}
	
	@Autowired
	private RestTemplate restClient;
	
	
	
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
	
	private Usuario clientUsuarioActual() {
		String uri = "http://localhost:8002/user";
	    ResponseEntity<Usuario> response = restClient.getForEntity(
				uri,
				Usuario.class);
	    return response.getBody();
	}
	
	private List<Product> clientFindAllProducts() {
		String uri = "http://localhost:8001/find/all";
	    Product[] prodArr = restClient.getForObject(
				uri,
				Product[].class);
	    return new ArrayList<>(Arrays.asList(prodArr));
	}
	
	private List<Product> clientFindOffers() {
		String uri = "http://localhost:8001/find/offers";
	    Product[] prodArr = restClient.getForObject(
				uri,
				Product[].class);
	    return new ArrayList<>(Arrays.asList(prodArr));
	}
	
	private Product clientFindById(Long id) {
		String uri = "http://localhost:8001/find/id/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    return restClient.getForObject(
				uri,
				Product.class,
				params);
	}
	
	private Boolean clientExistsByName(String name) {
		List<Product> prods = clientFindAllProducts();
		for(Product prod : prods) {
			if(prod.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	private Product clientSave(Product prod) {
		String uri = "http://localhost:8001/save";
		return restClient.postForObject(
				uri,
				prod,
				Product.class);
	}
	
	private Product clientEditPrice(Double price, Long id) {
		String uri = "http://localhost:8001/edit/price/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    restClient.put(
	    		uri,
	    		price,
	    		params);
	    return clientFindById(id);
	}
	
	private Product clientPutOnSale(Long id) {
		String uri = "http://localhost:8001/edit/putOnSale/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    restClient.put(
	    		uri,
	    		params);
	    return clientFindById(id);
	}
	
	private Product clientRemoveOnSale(Long id) {
		String uri = "http://localhost:8001/edit/removeOnSale/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    restClient.put(
	    		uri,
	    		params);
	    return clientFindById(id);
	}
	
	private Product clientAddStock(Integer stock, Long id) {
		String uri = "http://localhost:8001/edit/stock/add/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    restClient.put(
	    		uri,
	    		stock,
	    		params);
	    return clientFindById(id);
	}
	
	private Product clientSellStock(Long id, Integer amount) {
		String uri = "http://localhost:8001/edit/stock/sell/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    restClient.put(
	    		uri,
	    		amount,
	    		params);
	    return clientFindById(id);
	}
	
	private Product clientAddStock(Long id, Integer amount) {
		String uri = "http://localhost:8001/edit/stock/add/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    restClient.put(
	    		uri,
	    		amount,
	    		params);
	    return clientFindById(id);
	}
	
	private Boolean clientCartIsEmpty() {
		String uri = "http://localhost:8002/user/cart/isEmpty";
	    Boolean res = restClient.getForObject(
				uri,
				Boolean.class);
	    return res;
	}
	
	private Boolean clientProdIsInCart(Long id) {
		String uri = "http://localhost:8002/user/cart/prodIsInCart/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    Boolean res = restClient.getForObject(
				uri,
				Boolean.class,
				params);
	    return res;
	}
	
	private void clientCartAddItem(CartItem item) {
		String uri = "http://localhost:8002/user/cart/add/item";
		restClient.put(
				uri,
				item);
	}
	
	private void clientCartRemoveItem(CartItem item) {
		String uri = "http://localhost:8002/user/cart/remove/item";
		restClient.put(
				uri,
				item);
	}
	
	private void clientCartAddAmount(Long prodId, Integer amount) {
		String uri = "http://localhost:8002/user/cart/add/amount/{prodId}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("prodId", prodId);
		restClient.put(
				uri,
				amount,
				params);
	}
	
	private void clientCartRemoveAmount(Long prodId, Integer amount) {
		String uri = "http://localhost:8002/user/cart/remove/amount/{prodId}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("prodId", prodId);
		restClient.put(
				uri,
				amount,
				params);
	}
	
	private CartItem clientFindItemByProdId(Long prodId) {
		String uri = "http://localhost:8002/user/item/{prodId}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("prodId", prodId);
	    return restClient.getForObject(
				uri,
				CartItem.class,
				params);
	}
	
	
	
	
	
	
	@GetMapping("/index")
	public String prodIndex(Model model) {
		if(clientIsLoggedIn() && clientEsAdmin()) {
			model.addAttribute("esAdmin", true);
		} else {
			model.addAttribute("esAdmin", false);
		}
		return "prod/prod-index";
	}
	
	@GetMapping("/all")
	public String viewProds(Model model) {
		
		List<Product> prods = clientFindAllProducts();
		if(prods.size() > 0) {
			model.addAttribute("prods", prods);
			return "prod/prod-list";
		}
		return "error/no-prods-error";
	}
	
	@GetMapping("/onSale")
	public String viewProdsOnSale(Model model) {
		
		List<Product> prods = clientFindOffers();
		if(prods.size() > 0) {
			model.addAttribute("prods", prods);
			return "prod/prod-list";
		}
		return "error/no-prods-error";
	}
	
	@GetMapping("/byId")
	public String viewProdById(@ModelAttribute("id") Long id, Model model) {
		
		Product prod = clientFindById(id);
		if(prod != null) {
			model.addAttribute("prod", prod);
			return "prod/prod-by-id";
		}
		return "error/prod-by-id-not-found-error";
	}
	
	
	
	
	
	@PostMapping("/save")
	public String saveProd(@ModelAttribute("name") String name,
						@ModelAttribute("price") Double price,
						@ModelAttribute("stock") Integer stock,
						@ModelAttribute("onSale") Boolean onSale,
						Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {
	    		
	    		if(clientExistsByName(name)) {
	    			model.addAttribute(name);
    	    		return "error/save-prod-already-exists-error";
	    		}
	    		if(name != null && price != null && stock != null && onSale != null) {
    				Product prod = new Product(name, price, stock, onSale);
    				clientSave(prod);
    				model.addAttribute("prod", prod);
    				return "prod/save-prod-ok";
    			}
    			return "error/save-prod-null-field-error";
	    		
	    		
	    	}
	    	return "error/forbidden-error";
	    }
	    return "error/loged-out-error";
		
	}
	
	@PostMapping("/edit/price")
	public String editProdPrice(@ModelAttribute("price") Double price,
						@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {

	    		Product prod = clientEditPrice(price,id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "prod/edit-prod-ok";
	    		}
	    		return "error/prod-by-id-not-found-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/edit/onSale/put")
	public String editProdPutOnSale(@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {

	    		Product prod = clientPutOnSale(id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "prod/edit-prod-ok";
	    		}
	    		return "error/prod-by-id-not-found-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/edit/onSale/remove")
	public String editProdRemoveOnSale(@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {
	    		
	    		Product prod = clientRemoveOnSale(id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "prod/edit-prod-ok";
	    		}
	    		return "error/prod-by-id-not-found-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/edit/stock/add")
	public String editProdStockAdd(@ModelAttribute("stock") Integer stock,
						@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {

	    		Product prod = clientAddStock(stock, id);
	    		if(prod != null) {
	    			model.addAttribute("prod", prod);
	    			return "prod/edit-prod-ok";
	    		}
	    		return "error/prod-by-id-not-found-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@GetMapping("/delete/byId")
	public String deleteProdById(@ModelAttribute("id") Long id, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {

	    		Product prod = clientFindById(id);
	    		if(prod != null) {
	    			service.deleteById(id);
	    			model.addAttribute("id", id);
	    			return "prod/delete-by-id";
	    		}
	    		return "error/prod-by-id-not-found-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	@GetMapping("/delete/all")
	public String deleteAllProds() {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {

	    		service.deleteAll();
	    		return "prod/delete-all-prods";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}
	
	/*@GetMapping("/resetIdCounter")
	public String resetIdCounter() {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
	    	if(usuarioActual.tienePermiso("admin")) {

	    		if(service.findAll().size() == 0) {
	    			service.resetIdCounter();
	    			return "prod/reset-id-counter-ok";
	    		}
	    		return "error/reset-id-counter-error";
	    		
	    	}
	    	return "error/forbidden-error";
	    }
		return "error/loged-out-error";
		
	}*/
	
	@PostMapping("/cart/add")
	public String cartAdd(@ModelAttribute("id") Long id,
						@ModelAttribute("name") String name,
						@ModelAttribute("amount") Integer amount, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {

			if(amount > 0) {
				Product prod = clientFindById(id);
				model.addAttribute("name", name);
				model.addAttribute("amount", amount);
				if(prod.hasEnoughStockToSell(amount)) {
					
					if(clientProdIsInCart(id)) {
						clientCartAddAmount(id, amount);
					} else {
						Product product = clientFindById(id);
						CartItem newItem = new CartItem(product, amount);
						clientCartAddItem(newItem);
					}
					clientSellStock(id, amount);
					return "prod/cart-add";
					
				} else {
					return "error/not-enough-stock-error";
				}
			}
			return "error/invalid-amount-error";
			
	    }
		return "error/loged-out-error";
		
	}
	
	@PostMapping("/cart/remove")
	public String cartRemove(@ModelAttribute("id") Long id,
						@ModelAttribute("name") String name,
						@ModelAttribute("amount") Integer amount, Model model) {

		Usuario usuarioActual = clientUsuarioActual();
		if(usuarioActual != null) {
			
			Cart cart = usuarioActual.getCart();
		    if(!clientCartIsEmpty()) {
		    	if(amount > 0) {
					model.addAttribute("name", name);
										
					if(clientProdIsInCart(id)) {
						
						CartItem item = clientFindItemByProdId(id);
						Integer newAmount = item.getAmount() - amount;
						if(newAmount > 0) {
							clientCartRemoveAmount(id, amount);
							clientAddStock(id, amount);
							model.addAttribute("amount", amount);
						} else {
							clientCartRemoveItem(item);
							clientAddStock(id, item.getAmount());
							model.addAttribute("amount", item.getAmount());
						}
					    return "prod/cart-remove";
					    
					} else {
						return "error/prod-not-in-cart-error";
					}
				}
				return "error/invalid-amount-error";
		    }
		    return "error/empty-cart-error";
			
	    }
		return "error/loged-out-error";
		
	}
	
	
	
	
}
