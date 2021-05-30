package com.springboot.app.users.service;

import java.util.List;

import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Usuario;

public interface IUserService {

	public Boolean isLoggedIn();
    public Boolean login(Usuario usuario);
    public Boolean logout();
    public Boolean tienePermiso(String permiso);
    public Boolean esAdmin();
    public Usuario getUsuarioActual();
	
	
	
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
	public PermisosUsuario deletePermiso(Long id);
}