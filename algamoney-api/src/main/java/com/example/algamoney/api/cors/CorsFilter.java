package com.example.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // ** Ordem de prioridade de execucao. "HIGHEST_PRECEDENCE" = alta, primeira, precede
/**
 * Esse "Filter" e necessario, pois o "Spring-Security" ainda nao trabalha direto com "OAuth2"
 */
public class CorsFilter implements Filter {

	private String originPermitida = "http://localhost:8000"; // TODO: Configurar para diferentes ambientes
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.setHeader("Access-Control-Allow-Origin", originPermitida); // ** Quais ambientes poderao acessar...
        response.setHeader("Access-Control-Allow-Credentials", "true"); // ** Serve para sinalizar que o "Cookie" deve ser enviado.
		
		/**
		 * Se a requisicao for um "OPTIONS" e for "originPermitida": Faz as validacoes aqui para evitar um erro do "Spring-Security"
		 * Se_Nao, segue com o "Filter"
		 */
        if ("OPTIONS".equals(request.getMethod()) && originPermitida.equals(request.getHeader("Origin"))) {
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS"); // ** Quais os metodos HTTP permitidos...
        	response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept"); // ** Quais os Headers permitidos...
        	response.setHeader("Access-Control-Max-Age", "3600"); // ** Tempo que o Browse fara a proxima requisicao...
			
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
		
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
