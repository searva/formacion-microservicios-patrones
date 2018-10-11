package com.in28minutes.microservices.springsecurityssoui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LimitServiceProxyController {
	
	 @Autowired
	 private OAuth2RestTemplate template;
	 
	 @Value("${limits-service.endpoint}")
	 private String endpoint;

	@GetMapping("/limits")
	public LimitsConfiguration retrieveLimitsFromConfigurations() {
		 ResponseEntity<LimitsConfiguration> entity = template.getForEntity(endpoint, LimitsConfiguration.class);
	     return entity.getBody();
	}
	

}
