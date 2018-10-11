package com.in28minutes.microservices.springsecurityssoui;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/** 
 * The main goal of @EnableOAuth2Sso is to allow user's to authenticate to the application using an external OAuth 2.0 provider. 
 * This is a very convenient authentication option an application can provide as it doesn't need to provide it's own authentication mechanism 
 * (e.g. httpSecurity.formLogin()) and instead leverages an external identity (OAuth 2.0) provider for authentication.
 *
 * The @EnableOAuth2Sso annotation registers the OAuth2ClientAuthenticationProcessingFilter, which uses a RestTemplate (the OAuth Client) 
 * to obtain an Access Token from the Provider. After the Client receives the Access Token from the Provider, it would typically call the 
 * UserInfo Endpoint to fetch user information in order to create the Authentication. However, since you are using JWT based tokens, the 
 * DefaultAccessTokenConverter.extractAuthentication() is called (used by JwtAccessTokenConverter) to extract the information 
 * (using user_name and authorities claims) to create the OAuth2Authentication and establish an authenticated session. The Access Token 
 * used to obtain the user information is used once only during authentication. After the user is authenticated, that Access Token is never 
 * used again. Moreover, the refresh_token grant is not applicable for this specific OAuth Client scenario (flow).
 * 
 * 
 * **/

@EnableOAuth2Sso
@Configuration
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/callback/", "/webjars/**", "/error**")
				.permitAll().anyRequest().authenticated();
	}
	
	
	//Realmente esta configuración para comprobar el token en local no debería ser así en un caso real
	//se supone que la clave solo se comparte con modulos de confianza que forman parte del mismo sistema.
	//Una aplicación externa como la que simula ser esta, sin esta configuración de token service, iria al endpoint
	//de userinfo del servidor de aplicaciones papra validar el token.
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
	
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
	        OAuth2ProtectedResourceDetails details) {
	    return new OAuth2RestTemplate(details, oauth2ClientContext);
	}

}