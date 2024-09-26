package org.mql.laktam.speedreadbackend.business;

public interface FollowService {
	public boolean isFollowing(String followerUsername, String followedUsername);
	public void followUser(String followerUsername, String followedUsername);
    public void unfollowUser(String followerUsername, String followedUsername);

}