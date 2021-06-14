package com.springboot.app.users.model;

import javax.persistence.*;

import com.springboot.app.users.dto.UsuarioDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="usuarios_permisos",
		joinColumns={@JoinColumn(name="usuario_id")},
		inverseJoinColumns={@JoinColumn(name="permiso_id")})
    private Set<PermisosUsuario> permisos = new HashSet<>();
    
    @OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="cart_id")
    private Cart cart = new Cart();

    
    
    

	public Usuario() {
    	
	}
    
    public Usuario(String username, String password, PermisosUsuario permiso) {
		this.username = username;
		this.password = password;
		this.permisos.add(permiso);
	}
    
    public Usuario(UsuarioDTO dto, PermisosUsuario permiso) {
    	this.username = dto.getUsername();
		this.password = dto.getPassword();
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



	/**
	 * 
	 */
	private static final long serialVersionUID = -6869558724185975338L;
}
