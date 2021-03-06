package com.springboot.app.products.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.products.model.Product;
import com.springboot.app.products.repository.IProductRepository;

@Service
public class ProductService implements IProductService {

	@Autowired
	private IProductRepository repository;
	
	@Override
	@Transactional
	public Product save(Product product) {
		return repository.save(product);
	}
	
	@Override
	@Transactional
	public List<Product> saveAll(List<Product> products) {
		List<Product> listToSave = new ArrayList<>();
		for(Product product : products) {
			if(repository.findByName(product.getName()).orElse(null) == null) {
				listToSave.add(product);
			}
		}
		return repository.saveAll(listToSave);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean hasEnoughStockToSell(Integer stockToSell, Long id) {
		Optional<Product> optProd = repository.findById(id);
		if(!optProd.isEmpty()) {
			Product prod = optProd.get();
			return prod.hasEnoughStockToSell(stockToSell);
		}
		return false;
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Product> findByName(String name) {
		/*
		Optional<Product> res = repository.findByName(name);
		if(res.isEmpty()) {
			return null;
		}
		return res.get();
		*/
		return repository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Product> findOffers() {
		return ((List<Product>) repository.findAll()).stream()
				.filter(p -> p.getOnSale())
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findList(List<Long> ids){
		return repository.findList(ids);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	
	
	
	
	@Override
	@Transactional
	public Product edit(Product product, Long id) {
		Optional<Product> productSearch = repository.findById(id);
		if(productSearch.isPresent()) {
			Product productInDB = repository.findById(id).get();
			productInDB.setName(product.getName());
			productInDB.setPrice(product.getPrice());
			productInDB.setStock(product.getStock());
			productInDB.setOnSale(product.getOnSale());
			return repository.save(productInDB);
		}
		return null;
	}
	
	@Override
	@Transactional
	public Product editPrice(Double price, Long id) {
		Optional<Product> productSearch = repository.findById(id);
		if(productSearch.isPresent()) {
			Product productInDB = repository.findById(id).get();
			productInDB.setPrice(price);
			return repository.save(productInDB);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<Product> editPriceMap(Map<Long, Double> idAndPrices) {
		List<Product> editedProducts = new ArrayList<>();
		for(Long id : idAndPrices.keySet()) {
			Optional<Product> productSearch = repository.findById(id);
			if(productSearch.isPresent()) {
				Product productInDB = repository.findById(id).get();
				productInDB.setPrice(idAndPrices.get(id));
				editedProducts.add(productInDB);
			}
		}
		return repository.saveAll(editedProducts);
	}
	
	@Override
	@Transactional
	public Product removeOnSale(Long id) {
		Optional<Product> productSearch = repository.findById(id);
		if(productSearch.isPresent()) {
			Product productInDB = repository.findById(id).get();
			productInDB.setOnSale(false);
			return repository.save(productInDB);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<Product> removeOnSaleList(List<Long> ids) {
		List<Product> editedProducts = new ArrayList<>();
		for(Long id : ids) {
			Optional<Product> productSearch = repository.findById(id);
			if(productSearch.isPresent()) {
				Product productInDB = repository.findById(id).get();
				productInDB.setOnSale(false);
				editedProducts.add(productInDB);
			}
		}
		return repository.saveAll(editedProducts); 
	}
	
	@Override
	@Transactional
	public Product putOnSale(Long id) {
		Optional<Product> productSearch = repository.findById(id);
		if(productSearch.isPresent()) {
			Product productInDB = repository.findById(id).get();
			productInDB.setOnSale(true);
			return repository.save(productInDB);
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<Product> putOnSaleList(List<Long> ids) {
		List<Product> editedProducts = new ArrayList<>();
		for(Long id : ids) {
			Optional<Product> productSearch = repository.findById(id);
			if(productSearch.isPresent()) {
				Product productInDB = repository.findById(id).get();
				productInDB.setOnSale(true);
				editedProducts.add(productInDB);
			}
		}
		return repository.saveAll(editedProducts); 
	}
	
	@Override
	@Transactional
	public Product addStock(Integer stock, Long id) {
		Optional<Product> productSearch = repository.findById(id);
		if(productSearch.isPresent()) {
			Product productInDB = repository.findById(id).get();
			productInDB.setStock(productInDB.getStock() + stock);
			return repository.save(productInDB);
		}
		return null;
	}

	@Override
	@Transactional
	public List<Product> addStockMap(Map<Long, Integer> idAndStocks) {
		List<Product> editedProducts = new ArrayList<>();
		for(Long id : idAndStocks.keySet()) {
			Optional<Product> productSearch = repository.findById(id);
			if(productSearch.isPresent()) {
				Product productInDB = repository.findById(id).get();
				productInDB.setStock( productInDB.getStock() + idAndStocks.get(id) );
				editedProducts.add(productInDB);
			}
		}
		return repository.saveAll(editedProducts);
	}
	
	@Override
	@Transactional
	public Product sellStock(Integer stockToSell, Long id) {
		Optional<Product> productSearch = repository.findById(id);
		if(productSearch.isPresent()) {
			Product productInDB = repository.findById(id).get();
			if(productInDB.hasEnoughStockToSell(stockToSell)) {
				productInDB.setStock(productInDB.getStock() - stockToSell);
				return repository.save(productInDB);
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<Product> sellStockMap(Map<Long, Integer> idAndStocks) {
		List<Product> editedProducts = new ArrayList<>();
		for(Long id : idAndStocks.keySet()) {
			Optional<Product> productSearch = repository.findById(id);
			if(productSearch.isPresent()) {
				Product productInDB = repository.findById(id).get();
				Integer stockToSell = idAndStocks.get(id);
				if(productInDB.hasEnoughStockToSell(stockToSell)) {
					productInDB.setStock(productInDB.getStock() - stockToSell);
					editedProducts.add(productInDB);
				}
			}
		}
		return editedProducts;
	}
	
	/*@Override
	@Transactional
	public Sale sell(Integer stockToSell, Long id) {
		Product productInDB = repository.findById(id).orElse(null);
		if(productInDB != null && hasEnoughStock(productInDB, stockToSell)) {
			productInDB.setStock(productInDB.getStock() - stockToSell);
			repository.save(productInDB);
			Sale sale = new Sale(productInDB.getName(),stockToSell);
			return sale;
		} 
		return null;
	}

	@Override
	@Transactional
	public List<Sale> sellMap(Map<Long, Integer> idAndStocks) {
		List<Sale> sales = new ArrayList<>();
		for(Long id : idAndStocks.keySet()) {
			Product productInDB = repository.findById(id).orElse(null);
			if(productInDB != null) {
				Integer stockToSell = idAndStocks.get(id);
				if(hasEnoughStock(productInDB, stockToSell)) {
					productInDB.setStock(productInDB.getStock() - stockToSell);
					repository.save(productInDB);
					Sale sale = new Sale(productInDB.getName(), stockToSell);
					sales.add(sale);
				}
			}
		}
		return sales;
	}*/
	
	
	
	
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteAll() {
		repository.deleteAll();
	}
	
}
