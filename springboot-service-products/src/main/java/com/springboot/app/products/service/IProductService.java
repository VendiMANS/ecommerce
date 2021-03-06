package com.springboot.app.products.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.springboot.app.products.model.Product;

public interface IProductService {

	
	
	public Product save(Product product);
	public List<Product> saveAll(List<Product> products);

	public Optional<Product> findById(Long id);
	public Optional<Product> findByName(String name);
	public List<Product> findOffers();
	public List<Product> findList(List<Long> ids);
	public List<Product> findAll();
	
	public Boolean hasEnoughStockToSell(Integer stockToSell, Long id);

	public Product edit(Product product, Long id);
	public Product editPrice(Double price, Long id);
	public List<Product> editPriceMap(Map<Long, Double> idAndPrices);
	public Product putOnSale(Long id);
	public List<Product> putOnSaleList(List<Long> ids);
	public Product removeOnSale(Long id);
	public List<Product> removeOnSaleList(List<Long> ids);
	public Product addStock(Integer stock, Long id);
	public List<Product> addStockMap(Map<Long, Integer> idAndStocks);
	public Product sellStock(Integer stock, Long id);
	public List<Product> sellStockMap(Map<Long, Integer> idAndStocks);
	
    public void deleteById(Long id);
    public void deleteAll();
    
}
