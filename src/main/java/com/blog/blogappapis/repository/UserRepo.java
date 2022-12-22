package com.blog.blogappapis.repository;

import com.blog.blogappapis.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
