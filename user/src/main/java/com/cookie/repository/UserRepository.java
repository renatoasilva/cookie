package com.cookie.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.cookie.model.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "users/repo")
public interface UserRepository extends MongoRepository<User, String> {

	public User findByFirstName(@Param("firstName") String firstName);

	public List<User> findByLastName(@Param("lastName") String lastName);

	public User findByEmail(@Param("email") String email);

	public User findById(@Param("id") String id);
}
