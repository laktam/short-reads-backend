package org.mql.laktam.speedreadbackend.controllers;

import java.util.List;

import org.mql.laktam.speedreadbackend.business.PostService;
import org.mql.laktam.speedreadbackend.models.Post;
import org.mql.laktam.speedreadbackend.models.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("posts")
public class PostController {
	@Autowired
	private PostService postService;


	@Operation(summary = "Create a new post", responses = {
        @ApiResponse(responseCode = "200", description = "Post added successfully",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
    })
	@PostMapping("/new/{username}")
	public ResponseEntity<?> newPost(@PathVariable String username, @RequestParam("content") String content,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		postService.newPost(username, content, image);
		return ResponseEntity.ok(new ResponseMessage("Post added succesfully"));
	}
	
	 @Operation(summary = "Get all posts by username", responses = {
        @ApiResponse(responseCode = "200", description = "List of posts",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)))),
    })
	@GetMapping("/user/all/{username}")
	public ResponseEntity<List<Post>> getPostsByUsername(@PathVariable String username){
		return ResponseEntity.ok(postService.getPostsByUsername(username));
	}
	
	@Operation(summary = "Get paginated posts by username", responses = {
        @ApiResponse(responseCode = "200", description = "Paginated list of posts",
            content = @Content(schema = @Schema(implementation = Page.class)))
    })
	@GetMapping("/user/pagination/{username}")
    public ResponseEntity<Page<Post>> getPostsByUser(
            @PathVariable String username,
            Pageable pageable) {
        Page<Post> posts = postService.getPostsByUser(username, pageable);
        return ResponseEntity.ok(posts);
    }

	@Operation(summary = "Get the last 10 posts by username", responses = {
        @ApiResponse(responseCode = "200", description = "List of the latest 10 posts",
            content = @Content(schema = @Schema(implementation = Page.class)))
    })
	@GetMapping("/user/last/{username}")
    public ResponseEntity<Page<Post>> getLastPostsByUser(
            @PathVariable String username) {
        Page<Post> posts = postService.getPostsByUser(username, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt")));
        return ResponseEntity.ok(posts);
    }
	
	

}
