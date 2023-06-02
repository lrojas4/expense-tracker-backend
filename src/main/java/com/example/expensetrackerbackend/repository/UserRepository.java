package com.example.expensetrackerbackend.repository;
import com.example.expensetrackerbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //Login
    boolean existsByEmail(String email);

    //Register
    User findUserByEmail(String email);
}
