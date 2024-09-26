package org.mql.laktam.speedreadbackend.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import org.mql.laktam.speedreadbackend.business.JwtService;
import org.mql.laktam.speedreadbackend.business.UserService;
import org.mql.laktam.speedreadbackend.jwtutils.JwtUserDetailsService;
import org.mql.laktam.speedreadbackend.jwtutils.TokenManager;
import org.mql.laktam.speedreadbackend.models.Profile;
import org.mql.laktam.speedreadbackend.models.ProfileUpdateResponse;
import org.mql.laktam.speedreadbackend.models.User;
import org.mql.laktam.speedreadbackend.models.jwt.JwtSignupRequest;
import org.mql.laktam.speedreadbackend.models.jwt.JwtLoginRequest;
import org.mql.laktam.speedreadbackend.models.jwt.JwtResponse;
import org.mql.laktam.speedreadbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username){
		Optional<User> userOpt = userService.findByUsername(username);
		if(userOpt.isPresent()) {
			User user = userOpt.get();			
			return ResponseEntity.ok(user.toProfile());
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
		}
	}
	
	

    @PostMapping("/uploadProfilePicture/{username}")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable String username,
            @RequestParam("image") MultipartFile image) {
        try {
            String relativePath = userService.saveProfilePicture(username, image);
            return ResponseEntity.ok(Collections.singletonMap("message", relativePath));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }
    
    @PutMapping("/update/{username}")
    public ResponseEntity<ProfileUpdateResponse> updateUser(
            @PathVariable String username,
            @RequestBody Profile updatedUser) {
        try {
            User user = userService.updateUser(username, updatedUser);
            String token = jwtService.createToken(user.getUsername());
            return ResponseEntity.ok(new ProfileUpdateResponse(token, user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}