package org.mql.laktam.speedreadbackend.repositories;

import org.mql.laktam.speedreadbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
