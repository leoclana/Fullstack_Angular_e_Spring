package com.example.algamoney.api.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

// @Configuration : Nao e necessario, pois o @@EnableWebSecurity je implementa o @Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ROLE");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/categorias").permitAll()  // todos podem acessar "/categorias"
		.anyRequest().authenticated()  // o resto, somente usuario autenticado
		.and().httpBasic()  // Seguranca basica
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Aplicacao nao ira criar Sessao 
		.and().csrf().disable();  //Desabilitar o "Cross Site Request Forgery" (JavaScript Inject)
	}

}
