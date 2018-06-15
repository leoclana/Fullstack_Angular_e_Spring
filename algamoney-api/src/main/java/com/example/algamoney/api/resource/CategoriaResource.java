package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		
		//Categoria categoriaSalva = 
		categoriaRepository.save(categoria);
		
		/**
		 * ServletUriComponentsBuilder.fromCurrentRequestUri().path : pega a URL origem da requisicao 
		 * ("/{codigo}").buildAndExpand(categoriaSalva.getCodigo()) : adiciona o codigo da categoria no path
		 * .toUri();                                                : converte em um URT
		 */
		URI uri = 
			ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
			.buildAndExpand(categoria.getCodigo())
			.toUri();
		
		/**
		 * Seta um novo atributo "Location" no "Headers" do "HTTP response" a URL para consultar, via requisiao, a categoria Criada
		 * Ex.: http://localhost:8080/categorias/6
		 */
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoria);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		 Categoria categoria = categoriaRepository.findOne(codigo);
		 return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
}
