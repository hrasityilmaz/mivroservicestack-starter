package art.timestop.accountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountapiApplication.class, args);
	}

}
