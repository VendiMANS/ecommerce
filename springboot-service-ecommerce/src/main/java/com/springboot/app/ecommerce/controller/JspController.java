package com.springboot.app.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.app.ecommerce.service.EcommerceService;

@Controller
@RequestMapping("/api/view")
public class JspController {

	@Autowired
	private EcommerceService ecommerceService;
	
	public JspController(EcommerceService ecommerceService) {
		this.ecommerceService = ecommerceService;
	}
	
	
	/*
	@GetMapping("/products")
	public String viewProducts(Model model) {
		model.addAttribute("products", ecommerceService.findAll());
		return "view-sales";
	}
	*/
	
	
	
	
	@GetMapping("/sales")
	public String viewSales(Model model) {
		model.addAttribute("sales", ecommerceService.findAll());
		return "view-sales";
	}
}
