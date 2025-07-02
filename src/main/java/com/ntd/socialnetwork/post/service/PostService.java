package com.ntd.socialnetwork.post.service;


import com.ntd.socialnetwork.common.exception.AppException;
import com.ntd.socialnetwork.common.exception.ErrorCode;
import com.ntd.socialnetwork.post.dto.request.PostCreationRequest;
import com.ntd.socialnetwork.post.dto.request.PostDeleteRequest;
import com.ntd.socialnetwork.post.dto.response.PostResponse;
import com.ntd.socialnetwork.post.exception.PostErrorCode;
import com.ntd.socialnetwork.post.exception.PostException;
import com.ntd.socialnetwork.post.mapper.PostMapper;
import com.ntd.socialnetwork.post.model.Post;
import com.ntd.socialnetwork.post.repository.PostRepository;
import com.ntd.socialnetwork.user.service.UserService;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PostService {
    PostRepository postRepository;
    PostMapper postMapper;
    UserService userService;

    public void createPost(PostCreationRequest postCreationRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = this.userService.getUserByUsername(authentication.getName());

        Post post = postMapper.toPost(postCreationRequest);
        post.setUserId(user.getId());

        this.postRepository.save(post);
    }

    public List<PostResponse> getPosts() {
        return this.postRepository.findAll()
                .stream().map(postMapper::toPostResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(PostDeleteRequest postDeleteRequest) {
        Post post = postRepository.findById(postDeleteRequest.getId())
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        UserResponse currentUser = userService.getUserById(post.getUserId());

        var authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!authenticatedUsername.equals(currentUser.getUsername())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        postRepository.delete(post);
    }

}
