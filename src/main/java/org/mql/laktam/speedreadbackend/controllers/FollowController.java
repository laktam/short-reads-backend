package org.mql.laktam.speedreadbackend.controllers;

import java.util.Collections;

import org.mql.laktam.speedreadbackend.business.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/follows")
public class FollowController {
	@Autowired
	private FollowService followService;

	@Operation(summary = "Check if a user is following another user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Returns true if following, otherwise false",
                content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
	@GetMapping("/isFollowing/{followerUsername}/{followedUsername}")
	public ResponseEntity<Boolean> isFollowing(@PathVariable String followerUsername,
			@PathVariable String followedUsername) {
		return ResponseEntity.ok(followService.isFollowing(followerUsername, followedUsername));
	}

	@Operation(summary = "Follow a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Follow action successful",
                content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    })
	@GetMapping("/follow/{followerUsername}/{followedUsername}")
	public ResponseEntity<?> follow(@PathVariable String followerUsername, @PathVariable String followedUsername) {
		followService.followUser(followerUsername, followedUsername);
        return ResponseEntity.ok(new ResponseMessage(followerUsername + " started following " + followedUsername));
	}

	 @Operation(summary = "Unfollow a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Unfollow action successful",
                content = @Content(schema = @Schema(implementation = String.class)))
    })
	@GetMapping("/unfollow/{followerUsername}/{followedUsername}")
	public ResponseEntity<?> unfollow(@PathVariable String followerUsername, @PathVariable String followedUsername) {
		followService.unfollowUser(followerUsername, followedUsername);
		return ResponseEntity.ok(new ResponseMessage(followerUsername + " unfollowed " + followedUsername));
	}
}
