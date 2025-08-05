package art.timestop.usersapi.repository;

import org.springframework.data.repository.CrudRepository;

import art.timestop.usersapi.entity.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}