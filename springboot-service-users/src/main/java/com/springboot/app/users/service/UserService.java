package com.springboot.app.users.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Usuario;
import com.springboot.app.users.repository.IPermisosUsuarioRepository;
import com.springboot.app.users.repository.IUsuarioRepository;

@Service
public class UserService implements IUserService {

	@Autowired
    private IUsuarioRepository usuarioRepo;
	
    @Autowired
    private IPermisosUsuarioRepository permisosRepo;

    
    
    private Usuario usuarioActual;
    
    
    
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
    public Boolean tienePermiso(String permiso) {
    	if(isLoggedIn()) {
    		return usuarioActual.getPermiso() == permiso;
    	}
    	return false;
    }
    
    @Override
    public Boolean esAdmin() {
    	if(isLoggedIn()) {
    		return usuarioActual.getPermiso().equals("admin");
    	}
    	return false;
    }
    
    @Override
    public Usuario getUsuarioActual() {
    	return usuarioActual;
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

	
	
	
	
}
