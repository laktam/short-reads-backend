package org.mql.laktam.speedreadbackend.business;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.mql.laktam.speedreadbackend.models.Post;
import org.mql.laktam.speedreadbackend.models.User;
import org.mql.laktam.speedreadbackend.repositories.PostRepository;
import org.mql.laktam.speedreadbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.jsonwebtoken.io.IOException;

@Service
public class PostServiceDefault implements PostService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Value("${file.post-images-dir}")
	private String uploadDir;
	@Value("${file.backgroundUrl-prefix}")
	private String backgroundUrlPrefix;
	
	@Override
	public void newPost(String username, String content, MultipartFile backgroundImage) {
		Optional<User> userOpt = userRepository.findByUsername(username);
		if(userOpt.isPresent()) {
			String backgroundUrl;
			if(backgroundImage != null && !backgroundImage.isEmpty()) {
				backgroundUrl = saveBackgroundImage(username, backgroundImage);
			}else {
				backgroundUrl = "";
			}
			Post newPost = new Post(userOpt.get(), content, backgroundUrl);
			postRepository.save(newPost);
		}
		
	}
	
	@Override
	public List<Post> getPostsByUsername(String username){
		return postRepository.findAllByUsername(username);
	}
	
//	@Override
//	public Page<Post> getPostsByUsername(String username, Pageable pageable) {
//        return postRepository.findPostsByUsername(username, pageable);
//    }
	
	public Page<Post> getPostsByUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByUser(user, pageable);
    }
	
	
	
	public String saveBackgroundImage(String username, MultipartFile image) {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        // Get the file's original content type
        String originalFileName = image.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        } else {
            // Default to .jpg
            fileExtension = ".jpg";
        }
        
        
        String fileName = "post-background" + username + "-" + System.currentTimeMillis() + fileExtension;
        File destinationFile = new File(directory, fileName);
        try {
			image.transferTo(destinationFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

        String relativePath = backgroundUrlPrefix + fileName;
        return relativePath;
    }

}
