package com.example.expensetrackerbackend.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.logging.Logger;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JWTUtils jwtUtils;
}
