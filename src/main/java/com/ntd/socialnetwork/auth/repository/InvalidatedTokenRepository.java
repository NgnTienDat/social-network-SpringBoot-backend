package com.ntd.socialnetwork.auth.repository;

import com.ntd.socialnetwork.auth.model.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
