package com.example.algamoney.api.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
/**
 * Toda vez que algum @Conttroller retornar no "Body" um objeto do tipo "OAuth2AccessToken" ira entrar nesses metodos 
 * 
 * @author llana
 *
 */
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {

	@Override
	/**
	 * Filtro para só executar o metodo "beforeBodyWrite" quando o retorno for TRUE
	 */
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("postAccessToken"); //** Valida se o metodo chamador for um "postAccessToken"  
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body, MethodParameter returnType,
			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		
		//Convertento "ServerHttp.." em "HttpServlet.." para ser usado no "Cookie"
		HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
		HttpServletResponse resp = ((ServletServerHttpResponse) response).getServletResponse();
		
		//** Como o metodo trata somente "OAuth2AccessToken" usamos o "DefaultOAuth2AccessToken" para convertet o "body" em "Token"
		DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) body;
		
		String refreshToken = body.getRefreshToken().getValue();
		adicionarRefreshTokenNoCookie(refreshToken, req, resp);
		removerRefreshTokenDoBody(token);//
		
		return body;
	}

	/**
	 * Para remover o "RefreshToken" do "body", ja que ele agora esta em um "Cookie"
	 * @param token
	 */
	private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
		token.setRefreshToken(null);
	}

	private void adicionarRefreshTokenNoCookie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true); // ** So pode ser acessado pelo HTTP
		refreshTokenCookie.setSecure(false); // ** Dese ser seguro(somente HTTPs)? // TODO: Mudar para true em producao
		refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token"); //* PAra qual camino o "Cookie" deve ser enviado?
		refreshTokenCookie.setMaxAge(2592000); // Tempo de validade desse "Cookie" 
		resp.addCookie(refreshTokenCookie);
	}

}
