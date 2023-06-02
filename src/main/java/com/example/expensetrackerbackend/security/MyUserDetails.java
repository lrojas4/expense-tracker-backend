package com.example.expensetrackerbackend.security;

import com.example.expensetrackerbackend.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {
    private final User user;

    public MyUserDetails(User user) {
        this.user = user;
    }




}
