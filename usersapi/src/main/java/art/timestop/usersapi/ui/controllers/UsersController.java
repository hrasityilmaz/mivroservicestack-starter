package art.timestop.usersapi.ui.controllers;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import art.timestop.usersapi.dto.UserDto;
import art.timestop.usersapi.response.CreateUserResponseModel;
import art.timestop.usersapi.response.UserResponseModel;
import art.timestop.usersapi.service.UsersService;
import art.timestop.usersapi.ui.model.CreateUserRequestModel;



@RestController
@RequestMapping("/users")
public class UsersController {

    Environment env;
    UsersService usersService;
    
    private static Logger log = LoggerFactory.getLogger(UsersController.class);

    public UsersController(Environment env, UsersService usersService) {
        this.env = env;
        this.usersService = usersService;
    }
    
    @GetMapping("/status/check")
    public String status() {
        return "Working on port -> " + env.getProperty("local.server.port") + " with token " + env.getProperty("token.secret"); 
    }
    
    @PostMapping()
    public ResponseEntity<CreateUserResponseModel> createUser(@RequestBody CreateUserRequestModel userDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto =  modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = usersService.createUser(userDto);

         CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId){
		log.info("userid called {}", userId);
    	UserDto userDto = usersService.getUserByUserId(userId);
    	UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
    	
    	return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
    
}
