package com.example.expensetrackerbackend.controller;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.model.request.LoginRequest;
import com.example.expensetrackerbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/auth/")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register/")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerUser(@RequestBody User userObject) {
        return userService.registerUser(userObject);
    }

    @PostMapping(path = "/login/")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.loginUser(loginRequest);
    }
}
