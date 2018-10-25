package com.example.algamoney.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * O retorno do tipo "Optional" e para o caso do retorno ser Null, nao precisar ficar validando
	 * 
	 * @param email
	 * @return
	 */
	public Optional<Usuario> findByEmail(String email);
	
}
