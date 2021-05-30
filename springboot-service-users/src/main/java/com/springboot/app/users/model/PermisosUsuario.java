package com.springboot.app.users.model;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "permisos_usuario")
public class PermisosUsuario implements Serializable {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    
    
    public PermisosUsuario() {
    	
	}
    
    public PermisosUsuario(String name) {
		this.name = name;
	}

    
    
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2046824727013307626L;
}