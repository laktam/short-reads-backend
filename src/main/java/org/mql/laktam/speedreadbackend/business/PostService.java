package org.mql.laktam.speedreadbackend.business;

import org.springframework.web.multipart.MultipartFile;

public interface PostService {
	public void newPost(String username, String content, MultipartFile backgroundImage);
}
