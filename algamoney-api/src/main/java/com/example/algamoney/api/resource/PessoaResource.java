package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

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
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
//	@GetMapping
//	public List<Pessoa> listar() {
//		return pessoaRepository.findAll();
//	}
	
	@GetMapping
	public ResponseEntity<?> listar(){
		List<Pessoa> pessoas = pessoaRepository.findAll();
		ResponseEntity retorno = (pessoas.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoas));
		return retorno;
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build(); 
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	/**
	 * O Metodo retorna "void" mas o retorno "@ResponseStatus(HttpStatus.NO_CONTENT)" é o "204" = sucesso, mas não tenho nada para retornar.
	 * 
	 * @param codigo
	 */
	public void remover(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	@PostMapping("/{codigo}/trocaStatus")
	public ResponseEntity<Pessoa> trocaStatusAtivo(@PathVariable Long codigo, HttpServletResponse response){
		ResponseEntity retorno;
		Pessoa pessoa = pessoaRepository.findOne(codigo);
		if(pessoa != null) {
			pessoa.setAtivo( pessoa.getAtivo() ? false : true);
			pessoaRepository.save(pessoa);
			publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa.getCodigo()));
			retorno = ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body(pessoa);
		}else {
			retorno = ResponseEntity.notFound().build();
		}
		
		return retorno; 
	}
		
}
