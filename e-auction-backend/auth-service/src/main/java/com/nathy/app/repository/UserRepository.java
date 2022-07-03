package com.nathy.app.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nathy.app.entity.User;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);
	//Optional<User> findByUsernameOrEmail(String username,String email);
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByPassword(String password);
}
