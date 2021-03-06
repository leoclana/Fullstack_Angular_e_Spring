package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration // Nao e necessario, pois o @@EnableWebSecurity je implementa o @Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true) // Habilita a seguranca nos metodos.
 public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	//@Override : Era para o uso do "extends WebSecurityConfigurerAdapter"
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ROLE");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	//protected void configure(HttpSecurity http) throws Exception { //"protected" Era para o uso do "extends WebSecurityConfigurerAdapter"
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/categorias").permitAll()  // todos podem acessar "/categorias" || Ps.: Na aula "6.12" esse "permitAll" perde o sentido, ja que foi implementado o "@PreAuthorize" nos metodos de "CategoriaResource".
		.anyRequest().authenticated()  // o resto, somente usuario autenticado
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Aplicacao nao ira criar Sessao 
		.and().csrf().disable();  //Desabilitar o "Cross Site Request Forgery" (JavaScript Inject)
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	/**
	 * Para conbseguir fazer a seguranca dos metodos com OAuth2
	 * @return
	 */
	public MethodSecurityExpressionHandler createExpressionHandler() {
		return new OAuth2MethodSecurityExpressionHandler();
	}
	
}
