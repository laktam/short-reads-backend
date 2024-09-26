package org.mql.laktam.speedreadbackend.business;

import org.mql.laktam.speedreadbackend.models.Follow;
import org.mql.laktam.speedreadbackend.models.User;
import org.mql.laktam.speedreadbackend.repositories.FollowRepository;
import org.mql.laktam.speedreadbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceDefault implements FollowService{
	@Autowired
	private FollowRepository followRepository;
	@Autowired
    private UserRepository userRepository;
	
	@Override
	public boolean isFollowing(String followerUsername, String followedUsername) {
		return followRepository.isFollowing(followerUsername, followedUsername);
	}

	
	public void followUser(String followerUsername, String followedUsername) {
        User follower = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new UsernameNotFoundException("Follower not found"));
        User followed = userRepository.findByUsername(followedUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User to follow not found"));
        Follow follow = new Follow(follower, followed);
        followRepository.save(follow);
    }

    public void unfollowUser(String followerUsername, String followedUsername) {
        User follower = userRepository.findByUsername(followerUsername)
            .orElseThrow(() -> new UsernameNotFoundException("Follower not found"));
        User followed = userRepository.findByUsername(followedUsername)
            .orElseThrow(() -> new UsernameNotFoundException("User to unfollow not found"));

        Follow follow = followRepository.findByFollowerAndFollowed(follower, followed)
            .orElseThrow(() -> new IllegalStateException("Follow relationship does not exist"));

        followRepository.delete(follow);
    }
}
