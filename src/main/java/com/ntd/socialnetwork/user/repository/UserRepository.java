package com.ntd.socialnetwork.user.repository;

import com.ntd.socialnetwork.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    boolean existsUserByUsername(String username);
    Optional<User> findByUsername(String username);
}
