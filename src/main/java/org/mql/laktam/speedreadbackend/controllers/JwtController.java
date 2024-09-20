package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;

import org.mql.laktam.speedreadbackend.jwtutils.JwtUserDetailsService;
import org.mql.laktam.speedreadbackend.jwtutils.TokenManager;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 * Uses AuthenticationManager to authenticate the user and TokenManager to generate JWT tokens.
 */
@RestController
@CrossOrigin
public class JwtController {
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	// Get a JWT Token once user is authenticated, otherwise throw
	// BadCredentialsException
	@PostMapping("/login")
	public ResponseEntity<?> createToken(@RequestBody JwtLoginRequest request) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (DisabledException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is disabled");
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String jwtToken = tokenManager.generateJwtToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(jwtToken));
	}

	@PostMapping("/signup")
	   public ResponseEntity<?> registerUser(@RequestBody JwtSignupRequest request) {
	      if (userRepository.findByUsername(request.getUsername()).isPresent()) {
	         return ResponseEntity.badRequest().body("Username already exists.");
	      }
	      if (userRepository.findByEmail(request.getEmail()).isPresent()) {
	         return ResponseEntity.badRequest().body("Email already in use.");
	      }

	      String encodedPassword = passwordEncoder.encode(request.getPassword());

	      User newUser = new User(
	         request.getUsername(),
	         request.getEmail(),
	         encodedPassword
	      );

	      userRepository.save(newUser);
	      return ResponseEntity.ok(Collections.singletonMap("message", "Signup successful"));
	   }
}