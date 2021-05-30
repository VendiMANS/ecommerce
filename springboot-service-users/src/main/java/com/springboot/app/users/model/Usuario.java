package com.springboot.app.users.model;

import javax.persistence.*;

import com.springboot.app.users.dto.UsuarioDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;
    private String permiso;

    
    
    public Usuario() {
    	
	}
    
    public Usuario(String username, String password, String permiso) {
		this.username = username;
		this.password = password;
		this.permiso = permiso;
	}
    
    public Usuario(UsuarioDTO dto, String permiso) {
    	this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.permiso = permiso;
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

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }
    
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6869558724185975338L;
}
