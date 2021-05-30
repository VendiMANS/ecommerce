package com.springboot.app.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.app.users.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	@Query(value = "SELECT * FROM usuario WHERE username = ?1",
			nativeQuery = true)
	public Optional<Usuario> findByUsername(String username);
}