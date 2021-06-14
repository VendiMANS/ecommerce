package com.springboot.app.users.service;

import java.util.List;

import com.springboot.app.users.model.Cart;
import com.springboot.app.users.model.CartItem;
import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Usuario;

public interface IUserService {

	public Boolean isLoggedIn();
    public Boolean login(Usuario usuario);
    public Boolean logout();
    public Boolean tienePermiso(String permisoName);
    public Boolean esAdmin();
    public Usuario getUsuarioActual();
    
    public Cart getCurrentCart();
	
	
	
	public Usuario saveUsuario(Usuario usuario);
	public Usuario findUsuarioById(Long id);
	public Usuario findUsuarioByUsername(String username);
	public List<Usuario> findAllUsuarios();
	public Long countUsuarios();
	public Usuario deleteUsuario(Long id);
    
	//public Boolean permisosExisten(Usuario usuario);
    
    
    
    
	public PermisosUsuario savePermiso(PermisosUsuario permiso);
	public PermisosUsuario findPermisoById(Long id);
	public PermisosUsuario findPermisoByName(String name);
	public List<PermisosUsuario> findAllPermisos();
	public PermisosUsuario deletePermiso(Long id);
	
	
	

	public void addItem(CartItem item);
	public void removeItem(CartItem item);
	public void addAmount(Long prodId, Integer amount);
	public void removeAmount(Long prodId, Integer amount);
	public CartItem findItemByProdId(Long prodId);
	
	public Cart currentCart();
	public List<CartItem> purchaseCart();
	public void clearCart();
	public Boolean cartIsEmpty();
	public Boolean prodIsInCart(Long id);
}