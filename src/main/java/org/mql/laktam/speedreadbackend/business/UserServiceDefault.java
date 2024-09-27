package org.mql.laktam.speedreadbackend.business;

import java.io.File;
import java.util.Optional;

import org.mql.laktam.speedreadbackend.models.Profile;
import org.mql.laktam.speedreadbackend.models.User;
import org.mql.laktam.speedreadbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

@Service
public class UserServiceDefault implements UserService{
	@Autowired
	UserRepository userRepository;
	 @Value("${file.upload-dir}")
	 private String uploadDir;
	
	public UserServiceDefault() {
	
	}
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public String saveProfilePicture(String username, MultipartFile file) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = "profile-picture-" + user.getId() + "-" + System.currentTimeMillis() + ".jpg";
        File destinationFile = new File(directory, fileName);
        try {
			file.transferTo(destinationFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

        String relativePath = "static/" + fileName;
//        String relativePath = fileName;
        user.setProfilePictureUrl(relativePath);
        userRepository.save(user);

        return relativePath;
    }
	
	// only for username, email and description
	public User updateUser(String username, Profile newUser) throws Exception {
		Optional<User> userOpt = userRepository.findByUsername(username);
		if(userOpt.isPresent()) {
			// if new user field is changed it should be unique if it is username or email
			User user = userOpt.get();
			if(!user.getUsername().equals(newUser.getUsername())) {
				if(userRepository.existsByUsername(newUser.getUsername())) {
					throw new Exception("Username already exists");
				}
			}
			if(!user.getEmail().equals(newUser.getEmail())) {
				if(userRepository.existsByEmail(newUser.getEmail())) {
					throw new Exception("Email already exists");
				}
			}
			
			user.setUsername(newUser.getUsername());
			user.setEmail(newUser.getEmail());
			user.setDescription(newUser.getDescription());
			userRepository.save(user);
			return user;
		}
		throw new Exception("User not found");
	}

}
