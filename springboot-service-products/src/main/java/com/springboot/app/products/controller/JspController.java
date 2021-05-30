package com.springboot.app.products.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.app.products.model.Product;
import com.springboot.app.products.service.ProductService;

@Controller
@RequestMapping("/api/view/product")
public class JspController {

	@Autowired
	private ProductService service;
	
	public JspController(ProductService service) {
		this.service = service;
	}
	
	
	
	
	
	@GetMapping("/index")
	public String saleIndex() {
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
	
	@PostMapping("/edit/price")
	public String editProdPrice(@ModelAttribute("price") Double price,
						@ModelAttribute("id") Long id, Model model) {
		Product prod = service.editPrice(price,id);
		if(prod != null) {
			model.addAttribute("prod", prod);
			return "edit-prod-ok";
		}
		return "prod-by-id-not-found";
	}
	
	@PostMapping("/edit/onSale/put")
	public String editProdPutOnSale(@ModelAttribute("id") Long id, Model model) {
		Product prod = service.putOnSale(id);
		if(prod != null) {
			model.addAttribute("prod", prod);
			return "edit-prod-ok";
		}
		return "prod-by-id-not-found";
	}
	
	@PostMapping("/edit/onSale/remove")
	public String editProdRemoveOnSale(@ModelAttribute("id") Long id, Model model) {
		Product prod = service.removeOnSale(id);
		if(prod != null) {
			model.addAttribute("prod", prod);
			return "edit-prod-ok";
		}
		return "prod-by-id-not-found";
	}
	
	@PostMapping("/edit/stock/add")
	public String editProdStockAdd(@ModelAttribute("stock") Integer stock,
						@ModelAttribute("id") Long id, Model model) {
		Product prod = service.addStock(stock, id);
		if(prod != null) {
			model.addAttribute("prod", prod);
			return "edit-prod-ok";
		}
		return "prod-by-id-not-found";
	}
		
	
	
	@GetMapping("/delete/byId")
	public String deleteProdById(@ModelAttribute("id") Long id, Model model) {
		Optional<Product> search = service.findById(id);
		if(search.isPresent()) {
			service.deleteById(id);
			model.addAttribute("id", id);
			return "delete-by-id";
		}
		return "prod-by-id-not-found";
	}
	
	@GetMapping("/delete/all")
	public String deleteAllProds() {
		service.deleteAll();
		return "delete-all-prods";
	}
	
	
	
	@GetMapping("/resetIdCounter")
	public String resetIdCounter() {
		if(service.findAll().size() == 0) {
			service.resetIdCounter();
			return "reset-id-counter-ok";
		}
		return "reset-id-counter-error";
	}
}