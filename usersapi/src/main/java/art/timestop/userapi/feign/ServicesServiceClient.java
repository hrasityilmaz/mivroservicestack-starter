package art.timestop.userapi.feign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import art.timestop.usersapi.response.ServiceResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name="service-ws")
@Retry(name="service-ws")
@CircuitBreaker(name="service-ws",fallbackMethod = "getServicesFallBack")
public interface ServicesServiceClient {
	
	@GetMapping("/users/{id}/albums")
	public List<ServiceResponseModel> getServices(@PathVariable String id);
	
	default List<ServiceResponseModel> getServicesFallBack(String id, Throwable exception) {
		System.out.println("Param = " + id);
		System.out.println("Exception took place = " + exception.getMessage());
		
		return new ArrayList<>();
	}
}
