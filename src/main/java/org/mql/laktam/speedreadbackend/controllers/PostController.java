package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;
import java.util.List;

import org.mql.laktam.speedreadbackend.business.PostService;
import org.mql.laktam.speedreadbackend.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("posts")
public class PostController {
	@Autowired
	private PostService postService;

	@PostMapping("/new/{username}")
	public ResponseEntity<?> newPost(@PathVariable String username, @RequestParam("content") String content,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		postService.newPost(username, content, image);
		return ResponseEntity.ok(Collections.singletonMap("message", "post added succesfully"));
	}
	
	@GetMapping("/user/all/{username}")
	public ResponseEntity<List<Post>> getPostsByUsername(@PathVariable String username){
		return ResponseEntity.ok(postService.getPostsByUsername(username));
	}
	
	@GetMapping("/user/pagination/{username}")
    public ResponseEntity<Page<Post>> getPostsByUser(
            @PathVariable String username,
            Pageable pageable) {
        Page<Post> posts = postService.getPostsByUser(username, pageable);
        return ResponseEntity.ok(posts);
    }
	
	
	

}
