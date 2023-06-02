package com.example.expensetrackerbackend.security;
import com.example.expensetrackerbackend.model.User;
import com.example.expensetrackerbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Finds user by their email address.
     * @param username email for user.
     * @return The user details with that email.
     * @throws UsernameNotFoundException When email is not connected to a user.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(username);
        return new MyUserDetails(user);
    }
}
