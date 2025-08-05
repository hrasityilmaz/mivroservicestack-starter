package art.timestop.usersapi;

import java.lang.System.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import art.timestop.userapi.feign.FeignErrorDecoder;

@SpringBootApplication(scanBasePackages = {
	    "art.timestop.usersapi",
	    "art.timestop.userapi.feign"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"art.timestop.userapi.feign"})
public class UsersapiApplication {

    private final FeignErrorDecoder FeignErrorDecoder;

    UsersapiApplication(FeignErrorDecoder FeignErrorDecoder) {
        this.FeignErrorDecoder = FeignErrorDecoder;
    }

	public static void main(String[] args) {
		SpringApplication.run(UsersapiApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.ALL;
	}
	
	/*
	 * @Bean
	public FeignErrorDecoder FeignErrorDecoder() {
		return new FeignErrorDecoder();
	}
	 * */

}
