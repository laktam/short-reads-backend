package org.mql.laktam.speedreadbackend.business;

import org.mql.laktam.speedreadbackend.jwtutils.JwtUserDetailsService;
import org.mql.laktam.speedreadbackend.jwtutils.TokenManager;
import org.mql.laktam.speedreadbackend.models.User;
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
import org.springframework.stereotype.Service;

@Service
public class JwtServiceDefault implements JwtService{
	
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

	@Override
	public String login(String username, String password) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw e;
		}
		return createToken(username);
	}

	@Override
	public String createToken(String username) {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final String jwtToken = tokenManager.generateJwtToken(userDetails);
		return jwtToken;
	}

	@Override
	public User signup(String username, String email, String password) throws Exception {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new Exception("Username already exists.");
		}
		if (userRepository.findByEmail(email).isPresent()) {
			throw new Exception("Email already in use.");
		}

		String encodedPassword = passwordEncoder.encode(password);

		User newUser = new User(username, email, encodedPassword);

        userRepository.save(newUser);
		return newUser;
	}

}
