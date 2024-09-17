package org.mql.laktam.speedreadbackend.repositories;

import org.mql.laktam.speedreadbackend.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
