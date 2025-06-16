package com.ntd.socialnetwork.post.repository;

import com.ntd.socialnetwork.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(String id);
    Optional<Post> findById(String id);
}
