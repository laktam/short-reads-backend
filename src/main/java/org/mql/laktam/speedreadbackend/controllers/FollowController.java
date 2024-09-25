package org.mql.laktam.speedreadbackend.controllers;

import org.mql.laktam.speedreadbackend.business.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/follow")
public class FollowController {
	@Autowired
	private FollowService followService;
	
	@GetMapping("/isFollowing/{followerUsername}/{followedUsername}")
	public ResponseEntity<Boolean> isFollowing(@PathVariable String followerUsername , @PathVariable String followedUsername ){
		return ResponseEntity.ok(followService.isFollowing(followerUsername, followedUsername));
	}
}
