package com.example.expensetrackerbackend.service;

import com.example.expensetrackerbackend.exception.InformationExistException;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.repository.UserRepository;
import com.example.expensetrackerbackend.security.JWTUtils;
import com.example.expensetrackerbackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private JWTUtils jwtUtils;

    private MyUserDetails myUserDetails;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder, JWTUtils jwtUtils, MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.myUserDetails = myUserDetails;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user if email is not currently being used.
     * @param userObject User's information to be saved with their profile.
     * @return A new user.
     * @throws InformationExistException if email is already in use with a different agent.
     */
    public User registerAgent(User userObject) {
        if(!userRepository.existsByEmail(userObject.getEmail())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("An agent with email " + userObject.getEmail() + " already exists.");
        }
    }



}
