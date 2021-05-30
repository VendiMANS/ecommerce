package com.springboot.app.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.app.ecommerce.model.CartItem;
import com.springboot.app.ecommerce.model.Sale;
import com.springboot.app.ecommerce.service.EcommerceService;

@Controller
@RequestMapping("/api/view")
public class JspController {

	@Autowired
	private EcommerceService service;
	
	public JspController(EcommerceService service) {
		this.service = service;
	}
	
	
	
	
	
	@GetMapping("/sale/index")
	public String saleIndex() {
		return "sales/sales-index";
	}
	
	@GetMapping("/sale/allSales")
	public String viewSales(Model model) {
		List<Sale> sales = service.findAll();
		if(sales.size() > 0) {
			model.addAttribute("sales", sales);
			return "sales/sale-list";
		}
		return "sales/no-sales-error";
	}
	
	@GetMapping("/sale/allSalesByMonth")
	public String viewSalesByMonth(@ModelAttribute("month") Integer month, Model model) {
		List<Sale> sales = service.findAllByMonth(month);
		if(sales.size() > 0) {
			model.addAttribute("sales", sales);
			return "sales/sale-list";
		}
		model.addAttribute("month", month);
		return "sales/no-sales-in-month-error";
	}
	
	@GetMapping("/sale/saleById")
	public String viewSaleById(@ModelAttribute("id") Long id, Model model) {
		Optional<Sale> search = service.findById(id);
		if(search.isPresent()) {
			Sale sale = search.get();
			model.addAttribute("sale", sale);
			return "sales/sale-by-id";
		}
		return "sales/sale-by-id-error";
	}
	
	@GetMapping("/sale/deleteById")
	public String deleteSaleById(@ModelAttribute("id") Long id, Model model) {
		Optional<Sale> search = service.findById(id);
		if(search.isPresent()) {
			model.addAttribute("id", id);
			service.deleteById(id);
			return "sales/delete-by-id";
		}
		return "sales/sale-by-id-error";
	}
	
	@GetMapping("/sale/deleteAll")
	public String deleteAllSales() {
		service.deleteAll();
		return "sales/delete-all-sales";
	}
	
	
	
	
	
	@GetMapping("/cart/index")
	public String cartIndex() {
		return "cart/cart-index";
	}
	
	@GetMapping("/cart/get")
	public String viewCart(Model model) {
		List<CartItem> cart = service.getCart();
		if(cart != null) {
			model.addAttribute("cart", cart);
			return "cart/get";
		}
		return "cart/empty-cart-error"; //TODO el resto del carrito, primero vistas prod!!
	}
	
	
	
	
	
}
