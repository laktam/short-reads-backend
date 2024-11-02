package org.mql.laktam.speedreadbackend.controllers;


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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
	@Operation(summary = "Login to get a JWT token", description = "Validates user credentials and returns a JWT token on successful authentication.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully authenticated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
        @ApiResponse(responseCode = "403", description = "User is disabled",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    })
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


	@Operation(summary = "Register a new user", description = "Creates a new user account with the provided username, email, and password.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Signup successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(responseCode = "400", description = "Signup failed due to invalid input or duplicate entry",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseMessage.class)))
    })
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