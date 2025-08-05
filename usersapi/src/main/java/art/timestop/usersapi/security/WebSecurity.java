package art.timestop.usersapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Keep this
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Keep this
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import art.timestop.usersapi.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final UsersService usersService; // This should be a Spring @Service
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // This should be a Spring @Bean
    private final Environment env;

    public WebSecurity(
        UsersService usersService,
        BCryptPasswordEncoder bCryptPasswordEncoder,
        Environment env
    ) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, env, authenticationManager);
        authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));

        http.csrf(csrf -> csrf.disable());
        
        /*
         *  .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/users/status/check").permitAll()
            .requestMatchers("/actuator").permitAll()
            .requestMatchers(HttpMethod.POST, "/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
         * */
        
        http.authorizeHttpRequests(auth -> auth
        	.requestMatchers("/**").access(new WebExpressionAuthorizationManager("hasIpAddress('" + env.getProperty("gateway.ip") + "')"))
        	.requestMatchers(HttpMethod.GET, "/actuator/health").access(new WebExpressionAuthorizationManager("hasIpAddress('" + env.getProperty("gateway.ip") + "')"))
        	.requestMatchers(HttpMethod.GET, "/actuator/circuitbreakerevents").access(new WebExpressionAuthorizationManager("hasIpAddress('" + env.getProperty("gateway.ip") + "')"))
            .anyRequest().authenticated()
        );
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilter(authenticationFilter);

        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }
}