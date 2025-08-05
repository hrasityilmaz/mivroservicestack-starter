package art.timestop.usersapi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import art.timestop.userapi.feign.ServicesServiceClient;
import art.timestop.usersapi.dto.UserDto;
import art.timestop.usersapi.entity.UserEntity;
import art.timestop.usersapi.repository.UsersRepository;
import art.timestop.usersapi.response.ServiceResponseModel;

@Service
public class UsersServiceImpl implements UsersService {

    UsersRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	//RestTemplate restTemplate;
	ServicesServiceClient feignClient;
	Environment environmet;
	
	Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);
	
	public UsersServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ServicesServiceClient feignClient, Environment environmet) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.feignClient = feignClient;
		this.environmet = environmet;
	}



	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		userRepository.save(userEntity);
		
		UserDto returnVal = modelMapper.map(userDetails, UserDto.class);
		return returnVal;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepository.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),true, true, true, true, new ArrayList<>());
		
	}



	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
	    UserEntity userEntity = userRepository.findByUserId(userId);
	    if(userEntity == null) throw new UsernameNotFoundException("User Not found");
	    
	    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
	      		
	    List<ServiceResponseModel> serviceList = feignClient.getServices(userId);
	    		
	    userDto.setServices(serviceList);
	    
	    return userDto;
	}
}
