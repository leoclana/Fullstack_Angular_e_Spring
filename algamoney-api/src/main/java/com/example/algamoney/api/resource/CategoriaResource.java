package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	//@CrossOrigin(maxAge = 10, origins = {"http://qqOutroServidor:8000", "http://qqOutroServidor2" } ) 
	/**
	 *  "@CrossOrigin": Permite receber requisicao de origens diferentes 
	 *	"maxAge"      : Tempo em segundos que permitira receber outra requisicao depois do "Request Method: OPTIONS"
	 *	"origins"     : origens diferentes da aplicacao rest("http://localhost:8080") que podem chamar o servico.
	 *  "..."
	 *  
	 *  Ps.: Essa implementacao nao pode ser feita aqui por causa da "ainda-incompatibilidade do "Spring-Securitu" com "OAuth2".
	 *       Para solucionar foi criado a class "CrosFilter.java"
	 */
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
	/**
	 * O "@Valid" vai fazer a validacao do "Bean" e ao inves de retornar "500 Internal Server Error" passa a retornar "400 Bad Request"
	 * 
	 * @param categoria
	 * @param response
	 * @return
	 */
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		
		//Categoria categoriaSalva = 
		categoriaRepository.save(categoria);
		
		/**
		 * Startar o Evento "new RecursoCriadoEvent(..)" que vai chamar o "RecursoCriadoListener" 
		 * para setar o "response.setHeader("Location", uri.toASCIIString());" com a URL do novo recurso criado
		 */
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getCodigo()));
		
//		return ResponseEntity.created(uri).body(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		 Categoria categoria = categoriaRepository.findOne(codigo);
		 return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	/**
	 * O Metodo retorna "void" mas o retorno "@ResponseStatus(HttpStatus.NO_CONTENT)" é o "204" = sucesso, mas não tenho nada para retornar.
	 * 
	 * @param codigo
	 */
	public void remover(@PathVariable Long codigo) {
		categoriaRepository.delete(codigo);
	}
	
}
