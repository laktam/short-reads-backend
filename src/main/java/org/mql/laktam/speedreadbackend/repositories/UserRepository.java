package org.mql.laktam.speedreadbackend.repositories;

import java.util.Optional;

import org.mql.laktam.speedreadbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
}
