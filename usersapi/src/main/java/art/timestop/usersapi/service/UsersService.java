package art.timestop.usersapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import art.timestop.usersapi.dto.UserDto;

public interface UsersService extends UserDetailsService{
    UserDto createUser(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
    UserDto getUserByUserId(String userId);
}
