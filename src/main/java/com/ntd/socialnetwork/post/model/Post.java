package com.ntd.socialnetwork.post.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    String id;

    String content;

    String image;

    @Column(name = "create_at")
    Instant createdAt;

    @Column(name = "user_id", nullable = false)
    String userId;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}
