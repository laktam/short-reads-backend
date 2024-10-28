package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;
import java.util.Optional;
import org.mql.laktam.speedreadbackend.business.JwtService;
import org.mql.laktam.speedreadbackend.business.UserService;
import org.mql.laktam.speedreadbackend.models.Profile;
import org.mql.laktam.speedreadbackend.models.ProfileUpdateResponse;
import org.mql.laktam.speedreadbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtService jwtService;
	

    @Operation(
        summary = "Fetch user by username",
        description = "Retrieve a user profile based on the given username",
        responses = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully",
                         content = @Content(schema = @Schema(implementation = Profile.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
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
	
	
    @Operation(
        summary = "Upload profile picture",
        description = "Upload a profile picture for the specified user",
        responses = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "500", description = "Error uploading file")
        }
    )
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
    
    @Operation(
        summary = "Update user profile",
        description = "Update the user profile (except the profile picture) and receive a new token",
        responses = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                         content = @Content(schema = @Schema(implementation = ProfileUpdateResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found or update error")
        }
    )
    @PutMapping("/update/{username}")
    public ResponseEntity<?> updateUser(
            @PathVariable String username,
            @RequestBody Profile updatedUser) {
        try {
            User user = userService.updateUser(username, updatedUser);
            String token = jwtService.createToken(user.getUsername());
            return ResponseEntity.ok(new ProfileUpdateResponse(token, user.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}