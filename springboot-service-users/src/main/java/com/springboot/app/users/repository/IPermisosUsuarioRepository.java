package com.springboot.app.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.users.model.PermisosUsuario;
import com.springboot.app.users.model.Usuario;

public interface IPermisosUsuarioRepository extends JpaRepository<PermisosUsuario, Long> {
	Optional<PermisosUsuario> findByName(String name);
}
