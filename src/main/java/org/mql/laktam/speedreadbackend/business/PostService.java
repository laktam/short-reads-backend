package org.mql.laktam.speedreadbackend.business;

import java.util.List;

import org.mql.laktam.speedreadbackend.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
	public void newPost(String username, String content, MultipartFile backgroundImage);
	public List<Post> getPostsByUsername(String username);
	public Page<Post> getPostsByUser(String username, Pageable pageable);
	
}
