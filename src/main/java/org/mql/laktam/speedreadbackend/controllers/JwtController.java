package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;

import org.mql.laktam.speedreadbackend.business.JwtService;
import org.mql.laktam.speedreadbackend.models.jwt.JwtSignupRequest;
import org.mql.laktam.speedreadbackend.models.ResponseMessage;
import org.mql.laktam.speedreadbackend.models.jwt.JwtLoginRequest;
import org.mql.laktam.speedreadbackend.models.jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	private JwtService jwtService;

	// Get a JWT Token once user is authenticated, otherwise throw
	// BadCredentialsException
	@PostMapping("/login")
	public ResponseEntity<?> createToken(@RequestBody JwtLoginRequest request) throws Exception {
		String token = "";
		try {
			token = jwtService.login(request.getUsername(), request.getPassword());
		} catch (DisabledException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage("User is disabled"));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage("Invalid credentials"));
		}

		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody JwtSignupRequest request) {
		try {
			jwtService.signup(request.getUsername(), request.getEmail(), request.getPassword());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
		}
		return ResponseEntity.ok(new ResponseMessage("Signup successful"));
	}
}