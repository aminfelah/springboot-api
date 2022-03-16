package com.example.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.User;




@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findById(long id) ; 
	User findByUsername(String username);
	User findByEmail(String email); 
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findFirstByActivationToken(long token);
}