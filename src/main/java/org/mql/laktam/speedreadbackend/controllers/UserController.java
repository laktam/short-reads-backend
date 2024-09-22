package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;
import java.util.Optional;

import org.mql.laktam.speedreadbackend.jwtutils.JwtUserDetailsService;
import org.mql.laktam.speedreadbackend.jwtutils.TokenManager;
import org.mql.laktam.speedreadbackend.models.Profile;
import org.mql.laktam.speedreadbackend.models.User;
import org.mql.laktam.speedreadbackend.models.jwt.JwtSignupRequest;
import org.mql.laktam.speedreadbackend.models.jwt.JwtLoginRequest;
import org.mql.laktam.speedreadbackend.models.jwt.JwtResponse;
import org.mql.laktam.speedreadbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username){
		Optional<User> userOpt = userRepository.findByUsername(username);
		if(userOpt.isPresent()) {
			User user = userOpt.get();			
			return ResponseEntity.ok(new Profile(user.getUsername(), user.getDescription(), user.getFollowers().size(), user.getEmail()));
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
		}
	}

}