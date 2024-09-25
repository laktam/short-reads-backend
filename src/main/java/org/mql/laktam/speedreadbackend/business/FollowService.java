package org.mql.laktam.speedreadbackend.business;

public interface FollowService {
	public boolean isFollowing(String followerUsername, String followedUsername);
}