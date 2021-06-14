package com.springboot.app.users.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.springboot.app.users.model.Cart;
import com.springboot.app.users.model.CartItem;
import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Product;
import com.springboot.app.users.model.Sale;
import com.springboot.app.users.model.Usuario;
import com.springboot.app.users.repository.IPermisosUsuarioRepository;
import com.springboot.app.users.repository.IUsuarioRepository;

@Service
public class UserService implements IUserService {

	@Autowired
    private IUsuarioRepository usuarioRepo;
	
    @Autowired
    private IPermisosUsuarioRepository permisosRepo;
    
    @Autowired
	private RestTemplate restClient;

    
    
    private Usuario usuarioActual;
    
    
    
    private Sale clientSaleSave(Sale sale) {
		String uri = "http://localhost:8090/sale/save";
		return restClient.postForObject(
				uri,
				sale,
				Sale.class);
	}
    
    private Boolean clientProdStillExists(Long id) {
		String uri = "http://localhost:8001/find/id/{id}";
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("id", id);
	    Product prod = restClient.getForObject(
				uri,
				Product.class,
				params);
	    
	    return prod != null;
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
    
    
    
    @Override
    public Boolean isLoggedIn() {
    	return usuarioActual != null;
    }
    
    @Override
    public Boolean login(Usuario usuario) {
    	if(!isLoggedIn()) {
    		usuarioActual = usuario;
    		return true;
    	}
    	return false;
    }
    
    @Override
    public Boolean logout() {
    	if(isLoggedIn()) {
    		usuarioActual = null;
    		return true;
    	}
    	return false;
    }
    
    @Override
    public Boolean tienePermiso(String permisoName) {
    	if(isLoggedIn()) {
    		Optional<PermisosUsuario> search = permisosRepo.findByName(permisoName);
    		if(search.isEmpty()) {
    			return false;
    		}
    		PermisosUsuario permiso = search.get();
    		return usuarioActual.getPermisos().contains(permiso);
    	}
    	return false;
    }
    
    @Override
    public Boolean esAdmin() {
    	if(isLoggedIn()) {
    		Optional<PermisosUsuario> search = permisosRepo.findByName("admin");
    		if(search.isEmpty()) {
    			return false;
    		}
    		PermisosUsuario permiso = search.get();
    		return usuarioActual.getPermisos().contains(permiso);
    	}
    	return false;
    }
    
    @Override
    public Usuario getUsuarioActual() {
    	return usuarioActual;
    }
    
    @Override
    public Cart getCurrentCart() {
    	if(usuarioActual != null) {
    		return usuarioActual.getCart();
    	}
    	return null;
    }
    
    
    
    
    
	@Override
	public Usuario saveUsuario(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}

	@Override
	public Usuario findUsuarioById(Long id) {
		Optional<Usuario> search = usuarioRepo.findById(id);
		if(search.isPresent()) {
			Usuario entity = search.get();
			return entity;
		}
		return null;
	}
	
	@Override
	public Usuario findUsuarioByUsername(String username) {
		Optional<Usuario> search = usuarioRepo.findByUsername(username);
		if(search.isPresent()) {
			Usuario entity = search.get();
			return entity;
		}
		return null;
	}
	
	@Override
	public List<Usuario> findAllUsuarios() {
		return usuarioRepo.findAll();
	}
	
	@Override
	public Long countUsuarios() {
		return usuarioRepo.count();
	}

	@Override
	public Usuario deleteUsuario(Long id) {
		Optional<Usuario> search = usuarioRepo.findById(id);
		if(search.isPresent()) {
			Usuario entity = search.get();
			usuarioRepo.delete(entity);
			return entity;
		}
		return null;
	}
	/*
	@Override
	public Boolean permisosExisten(Usuario usuario) {
		for(String permiso : usuario.getPermisos()) {
			if(findPermisoByName(permiso) == null) {
				return false;
			}
		}
		return true;
	}*/

	
	
	@Override
	public PermisosUsuario savePermiso(PermisosUsuario permiso) {
		return permisosRepo.save(permiso);
	}

	@Override
	public List<PermisosUsuario> findAllPermisos(){
		return permisosRepo.findAll();
	}
	
	@Override
	public PermisosUsuario findPermisoById(Long id) {
		Optional<PermisosUsuario> search = permisosRepo.findById(id);
		if(search.isPresent()) {
			PermisosUsuario entity = search.get();
			return entity;
		}
		return null;
	}
	
	@Override
	public PermisosUsuario findPermisoByName(String name) {
		Optional<PermisosUsuario> search = permisosRepo.findByName(name);
		if(search.isPresent()) {
			PermisosUsuario entity = search.get();
			return entity;
		}
		return null;
	}

	@Override
	public PermisosUsuario deletePermiso(Long id) {
		Optional<PermisosUsuario> search = permisosRepo.findById(id);
		if(search.isPresent()) {
			PermisosUsuario entity = search.get();
			permisosRepo.delete(entity);
			return entity;
		}
		return null;
	}

	
	
	@Override
	@Transactional
	public void addItem(CartItem item) {
		
		if(clientProdStillExists(item.getProduct().getId())) {
			
			Cart cart = usuarioActual.getCart();
			Set<CartItem> items = cart.getItems();
			if(!items.contains(item)) {
				items.add(item);
				usuarioRepo.save(usuarioActual);
			}
			
		}
	}
	
	@Override
	@Transactional
	public void removeItem(CartItem item) {
		Cart cart = usuarioActual.getCart();
		Set<CartItem> items = cart.getItems();
		items.remove(item);
		usuarioRepo.save(usuarioActual);		
	}
	
	@Override
	@Transactional
	public void addAmount(Long prodId, Integer amount) {
		Cart cart = usuarioActual.getCart();
		Set<CartItem> items = cart.getItems();
		for(CartItem item : items) {
			if(item.getProduct().getId() == prodId) {
				
				if(clientProdStillExists(prodId)) {
				
					Integer newAmount = item.getAmount() + amount;
					item.setAmount(newAmount);
				
				} else {
					
					removeItem(item);
					
				}
				
				usuarioRepo.save(usuarioActual);
				break;
			}
		}
	}
	
	@Override
	@Transactional
	public void removeAmount(Long prodId, Integer amount) {
		Cart cart = usuarioActual.getCart();
		Set<CartItem> items = cart.getItems();
		for(CartItem item : items) {
			if(item.getProduct().getId() == prodId) {
				
				if(clientProdStillExists(prodId)) {
					
					Integer newAmount = item.getAmount() - amount;
					if(newAmount >= 0) {
						item.setAmount(newAmount);
					} else {
						item.setAmount(0);
					}
					
					usuarioRepo.save(usuarioActual);
					break;
					
				} else {
					
					removeItem(item);
					
				}
			}
		}
	}
	
	@Override
	@Transactional
	public CartItem findItemByProdId(Long prodId) {
		Cart cart = usuarioActual.getCart();
		if(cart != null) {
			Set<CartItem> items = cart.getItems();
			for(CartItem item : items) {
				if(item.getProduct().getId() == prodId) {
					return item;
				}
			}
		}
		return null;
	}
	
	
	
	@Override
	@Transactional
	public Cart currentCart(){
		
		if(isLoggedIn()) {
			return getCurrentCart();
		}
		return null;
	}
	
	
	
	@Override
	@Transactional
	public List<CartItem> purchaseCart() {
		if(currentCart() != null) {
			List<CartItem> items = (List<CartItem>) currentCart().getItems();
			for(CartItem item : items) {
				
				Product prod = item.getProduct();
				Integer amount = item.getAmount();
				
				if(clientProdStillExists(prod.getId())) {
					Sale newSale = new Sale(prod.getName(), amount);
					clientSaleSave(newSale);
				}
			}
			
			List<CartItem> purchase = new ArrayList<>(items);
			items.clear();
			usuarioRepo.save(usuarioActual);
			return purchase;
		}
		return null;
	}
	
	@Override
	@Transactional
	public void clearCart() {
		if(currentCart() != null) {
			List<CartItem> items = (List<CartItem>) currentCart().getItems();
			for(CartItem item : items) {
				
				Product prod = item.getProduct();
				Integer amount = item.getAmount();
				
				if(clientProdStillExists(prod.getId())) {
					clientAddStock(prod.getId(), amount);
				}
			}
			usuarioRepo.save(usuarioActual);
			items.clear();
		}
	}
	
	
	
	
	@Override
	@Transactional(readOnly = true)
	public Boolean cartIsEmpty() {
		if(isLoggedIn()) {
			return (getCurrentCart().getItems().size() == 0);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean prodIsInCart(Long id) {
		if(isLoggedIn()) {
			Set<CartItem> items = getCurrentCart().getItems();
			for(CartItem item : items) {
				if(item.getProduct().getId() == id) {
					return true;
				}
			}
			return false;
		}
		return null;
	}
	
	
	
	
}
