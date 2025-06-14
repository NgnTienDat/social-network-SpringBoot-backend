package com.ntd.socialnetwork.post.repository;

import com.ntd.socialnetwork.post.model.Post;
import com.ntd.socialnetwork.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<User, Long> {
    void save(Post post);
}
