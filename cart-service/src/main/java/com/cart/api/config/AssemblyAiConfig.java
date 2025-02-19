package com.cart.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assemblyai.api.AssemblyAI;
@Configuration
public class AssemblyAiConfig {

	@Bean
	public AssemblyAI assemblyAiClient() {
		
		return AssemblyAI.builder()
				.apiKey("b0b39141a89b412b8983a56de02f490a")
				.build();
		
	}
	

}
