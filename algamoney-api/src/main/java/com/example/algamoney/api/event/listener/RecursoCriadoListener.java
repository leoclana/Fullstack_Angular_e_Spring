package com.example.algamoney.api.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.event.RecursoCriadoEvent;

@Component
/**
 * Quando o evento de "new RecursoCriadoEvent(...)" ocorrer, este Listener sera executado. 
 * 
 * @author llana
 *
 */
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long codigo = recursoCriadoEvent.getCodigo();
		
		adicionarHeaderLocation(response, codigo);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		/**
		 * ServletUriComponentsBuilder.fromCurrentRequestUri()  : pega a URL origem da requisicao 
		 * .path("/{codigo}").buildAndExpand(codigo)            : adiciona o codigo da categoria no path
		 * .toUri();                                            : converte em um URT
		 */
		URI uri = 
				ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo)
				.toUri();
		/**
		 * Seta um novo atributo "Location" no "Headers" do "HTTP response" a URL para consultar, via requisiao, o Objeto criado
		 * Ex.: http://localhost:8080/categorias/6
		 */
		response.setHeader("Location", uri.toASCIIString());
	}
	
	

}
