package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;  // Ele que vai pegar o usuário e senha (admin, admin) do ResourceServerConfig
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//clients.jdbc(dataSource); // para o caso de usar em Banco de Dados..
		clients.inMemory()
				.withClient("angular")   // login da aplicação cliente
				.secret("@ngul@r0")      // senha da aplicação cliente
				.scopes("read", "write") // escopo de permicao/acessos
				.authorizedGrantTypes("password", "refresh_token") // "password": Indica o tipo do fluxo de criacao do TOKEN "usuario" e "senha" , "refresh_token" : na ispira��o do "TOKEN" ir� usar o "refresh_token" para gerar um novo TOKEN.
				.accessTokenValiditySeconds(1800) // tempo em que o TOKEN ficara valido, em segundos
				.refreshTokenValiditySeconds(3600 * 24) // tempo em que o "refresh_token" ficara valido, em segundos
			.and()
				.withClient("mobile")
				.secret("m0b1l30")
				.scopes("read")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(1800)
				.refreshTokenValiditySeconds(3600 * 24);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.reuseRefreshTokens(false) // Indica que toda vez que for solicitado um novo "TOKEN", um novo "refresh_token" tambem sera gerado e valido por mais <x-tempo>"(360 * 24)segundos"
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks"); // chave para gerar/descriptografar/validar o Token
		return accessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		//return new InMemoryTokenStore();
		return new JwtTokenStore(accessTokenConverter());
	}
	
}
