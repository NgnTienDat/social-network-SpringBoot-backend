package com.ntd.socialnetwork.user;

import com.ntd.socialnetwork.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
    boolean existsUserByUsername(String username);
    User findByUsername(String username);
}
