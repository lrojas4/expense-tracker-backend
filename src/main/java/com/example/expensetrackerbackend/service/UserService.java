package com.example.expensetrackerbackend.service;
import com.example.expensetrackerbackend.exception.InformationExistException;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.model.request.LoginRequest;
import com.example.expensetrackerbackend.model.response.LoginResponse;
import com.example.expensetrackerbackend.repository.UserRepository;
import com.example.expensetrackerbackend.security.JWTUtils;
import com.example.expensetrackerbackend.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private JWTUtils jwtUtils;

    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager,
                       @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, @Lazy MyUserDetails myUserDetails) {
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
     * Finds user by email address
     * @param email address we are looking for
     * @return user
     */
    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Registers a new user if email is not currently being used.
     * @param userObject User's information to be saved with their profile.
     * @return A new user.
     * @throws InformationExistException if email is already in use with a different user.
     */
    public User registerUser(User userObject) {
        if(!userRepository.existsByEmail(userObject.getEmail())) {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("A user with email " + userObject.getEmail() + " already exists.");
        }
    }

    /**
     * Finds an user by their email address.
     * @param email The email that we're searching for.
     * @return User associated with email.
     */
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * Logs in user with credentials given by user.
     * @param loginRequest The credentials to log in.
     * @return Authenticated user.
     */
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();

            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Error: username or password is incorrect"));
        }
    }
}
