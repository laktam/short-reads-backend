package org.mql.laktam.speedreadbackend.repositories;

import java.util.List;

import org.mql.laktam.speedreadbackend.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
	@Query("SELECT p FROM Post p WHERE p.user.username = ?1")
    List<Post> findAllByUsername(String username);

	@Query("SELECT p FROM Post p WHERE p.user.username = ?1")
	Page<Post> findPostsByUsername(String username, Pageable pageable);// for pagination to not load all posts at once
}
