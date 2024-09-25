package org.mql.laktam.speedreadbackend.repositories;

import org.mql.laktam.speedreadbackend.models.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {
	@Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Follow f " +
	           "WHERE f.follower.username = :followerUsername AND f.followed.username = :followedUsername")
	boolean isFollowing(@Param("followerUsername") String followerUsername, 
	                                        @Param("followedUsername") String followedUsername);

}
