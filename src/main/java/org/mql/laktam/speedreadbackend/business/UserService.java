package org.mql.laktam.speedreadbackend.business;

import java.util.Optional;

import org.mql.laktam.speedreadbackend.models.Profile;
import org.mql.laktam.speedreadbackend.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	public Optional<User>  findByUsername(String username);
	public String saveProfilePicture(String username, MultipartFile file);
	public User updateUser(String username, Profile profile) throws Exception;
}
