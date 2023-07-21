package com.bhagan.springsecurity.jwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bhagan.springsecurity.jwt.entity.User;

public interface UserRepository extends JpaRepository<User,Long>  {

    Optional<User> findByEmail(String email); 
    
}
