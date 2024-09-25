package org.mql.laktam.speedreadbackend.business;

import org.mql.laktam.speedreadbackend.repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceDefault implements FollowService{
	@Autowired
	private FollowRepository followRepository;

	@Override
	public boolean isFollowing(String followerUsername, String followedUsername) {
		return followRepository.isFollowing(followerUsername, followedUsername);
	}

}
