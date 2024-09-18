package org.mql.laktam.speedreadbackend.controllers;

import org.mql.laktam.speedreadbackend.jwtutils.JwtUserDetailsService;
import org.mql.laktam.speedreadbackend.jwtutils.TokenManager;
import org.mql.laktam.speedreadbackend.models.jwt.JwtRequest;
import org.mql.laktam.speedreadbackend.models.jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
   
   // Get a JWT Token once user is authenticated, otherwise throw BadCredentialsException
   @PostMapping("/login")
   public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest
      request) throws Exception {
      try {
         authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
      } catch (DisabledException e) {
         throw new Exception("USER_DISABLED", e);
      } catch (BadCredentialsException e) {
         throw new Exception("INVALID_CREDENTIALS", e);
      }
      final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
      final String jwtToken = tokenManager.generateJwtToken(userDetails);
      return ResponseEntity.ok(new JwtResponse(jwtToken));
   }
}