package art.timestop.userapi.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
	
	Environment environment;
	
	public FeignErrorDecoder(Environment environment) {
		super();
		this.environment = environment;
	}


	@Override
	public Exception decode(String methodKey, Response response) {
		
		switch (response.status()) {
		case 400:
			// return new BadRequestException();
			break;
		case 404:
		{
			if(methodKey.contains("getServices")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("services.exceptions.service-not-found"));
			}
			
			break;
		}
		
		default:
			return new Exception(response.reason());
		}
		
		return null;
		
	}
	
}
