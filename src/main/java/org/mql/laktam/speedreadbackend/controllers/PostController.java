package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;

import org.mql.laktam.speedreadbackend.business.PostService;
import org.mql.laktam.speedreadbackend.models.NewPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	    public ResponseEntity<?> newPost(
	            @PathVariable String username,
	            @RequestParam("content") String content, 
	            @RequestParam(value = "image", required = false) MultipartFile image
	    ) {
		postService.newPost(username, content, image);
		return ResponseEntity.ok(Collections.singletonMap("message", "post added succesfully"));
	}
}
