package com.ntd.socialnetwork.post.controller;

import com.ntd.socialnetwork.common.dto.response.APIResponse;
import com.ntd.socialnetwork.post.dto.request.PostCreationRequest;
import com.ntd.socialnetwork.post.model.Post;
import com.ntd.socialnetwork.post.service.PostService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping("/post")
    public ResponseEntity<APIResponse<Void>> createPost(@RequestBody @Valid PostCreationRequest postCreationRequest) {
        this.postService.createPost(postCreationRequest);
        APIResponse<Void> response = new APIResponse<>(
                true,
                "User created successfully",
                null,
                HttpStatus.CREATED.value()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
