package com.example.expensetrackerbackend.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
@PropertySource("classpath:application-dev.properties")
public class JWTUtils {

    Logger logger = Logger.getLogger(JWTUtils.class.getName());

    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-ms}")
    private int jwtExpirationMs;

}
