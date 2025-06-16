package com.ntd.socialnetwork.post.controller;

import com.ntd.socialnetwork.common.dto.response.APIResponse;
import com.ntd.socialnetwork.post.dto.request.PostCreationRequest;
import com.ntd.socialnetwork.post.dto.request.PostDeleteRequest;
import com.ntd.socialnetwork.post.dto.response.PostResponse;
import com.ntd.socialnetwork.post.model.Post;
import com.ntd.socialnetwork.post.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<APIResponse<Void>> createPost(@RequestBody @Valid PostCreationRequest postCreationRequest) {
        this.postService.createPost(postCreationRequest);
        APIResponse<Void> response = new APIResponse<>(
                true,
                "New post created successfully",
                null,
                HttpStatus.CREATED.value()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<APIResponse<List<PostResponse>>> getAllPost() {
        APIResponse<List<PostResponse>> response = new APIResponse<>(
                true,
                "Get posts successfully",
                this.postService.getPosts(),
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/posts")
    public ResponseEntity<APIResponse<Void>> deletePost(@RequestBody @Valid PostDeleteRequest postDeleteRequest) {
        this.postService.deletePost(postDeleteRequest);
        APIResponse<Void> response = new APIResponse<>(
                true,
                "Post was deleted successfully",
                null,
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
