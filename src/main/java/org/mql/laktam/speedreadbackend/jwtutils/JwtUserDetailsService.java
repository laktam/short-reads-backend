package org.mql.laktam.speedreadbackend.jwtutils;

import org.mql.laktam.speedreadbackend.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by email in the UserRepository
        org.mql.laktam.speedreadbackend.models.User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
//        System.out.println(user);
        // Return a Spring Security UserDetails object (User class)
        return new User(user.getUsername(), user.getPasswordHash(), Collections.emptyList());
    }
}
