package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("/listaX")
	/**
	 *  ResponseEntity<?> - indica um "return" de "ResponseEntity" que pode conter qualquer "JSON"
	 *  Ex.: return ResponseEntity.ok(categorias) ou  ResponseEntity.noContent() = "204 No Content" : não contem resultado
	 *   
	 * @return
	 */
	public ResponseEntity<?> listarX() {
		ResponseEntity retorno;
		List<Categoria> categorias = categoriaRepository.findAll();
		
		//**forcar lista vazia para teste **
		categorias.clear();
		
		//** ResponseEntity.notFound() = "404 Not Found" : url nao encontrada (nao e o melhor para esse caso) **
		//retorno = ( !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.notFound().build()); 
		
		//** ResponseEntity.noContent() = "204 No Content" : não contem resultado **
		retorno = ( !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build()); 
		
		return retorno;
	}
	
}
