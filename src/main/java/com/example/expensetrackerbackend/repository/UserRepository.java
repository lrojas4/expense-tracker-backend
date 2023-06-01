package com.example.expensetrackerbackend.repository;

import com.example.expensetrackerbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public class UserRepository extends JpaRepository<User, Long> {
}
