package art.timestop.discoveryservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class WebSecurity {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		 
		
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(auth -> auth
	        	.anyRequest().authenticated()
	        );
		http.httpBasic(Customizer.withDefaults());
		
		return http.build();
		
	}
	
	
}
