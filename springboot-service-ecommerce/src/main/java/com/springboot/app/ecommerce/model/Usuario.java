package com.springboot.app.ecommerce.model;

import javax.persistence.*;

import com.springboot.app.ecommerce.model.Cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Usuario {
	

    private Long id;
    private String username;
    private String password;
    private Set<PermisosUsuario> permisos = new HashSet<>();
    private Cart cart = new Cart();

    
    
    public Usuario() {
    	
	}
    
    public Usuario(String username, String password, PermisosUsuario permiso) {
		this.username = username;
		this.password = password;
		this.permisos.add(permiso);
	}
    
    
    
    public Boolean tienePermiso(String name) {
    	for(PermisosUsuario permiso : permisos) {
    		if(permiso.getName().equals(name)) {
    			return true;
    		}
    	}
    	return false;
    }
    

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<PermisosUsuario> getPermisos() {
        return permisos;
    }

    public void addPermiso(PermisosUsuario permiso) {
        this.permisos.add(permiso);
    }
    
    public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
    
    
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((permisos == null) ? 0 : permisos.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (permisos == null) {
			if (other.permisos != null)
				return false;
		} else if (!permisos.equals(other.permisos))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}



}
