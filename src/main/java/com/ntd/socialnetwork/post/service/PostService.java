package com.ntd.socialnetwork.post.service;


import com.ntd.socialnetwork.common.exception.AppException;
import com.ntd.socialnetwork.common.exception.ErrorCode;
import com.ntd.socialnetwork.post.dto.request.PostCreationRequest;
import com.ntd.socialnetwork.post.dto.response.PostResponse;
import com.ntd.socialnetwork.post.mapper.PostMapper;
import com.ntd.socialnetwork.post.model.Post;
import com.ntd.socialnetwork.post.repository.PostRepository;
import com.ntd.socialnetwork.user.UserRepository;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {
    PostRepository postRepository;
    UserRepository userRepository;
    PostMapper postMapper;

    public void createPost(PostCreationRequest postCreationRequest) {
        User user = this.userRepository.findById(postCreationRequest.getUserId()).orElseThrow(
                ()-> new AppException(ErrorCode.USER_NOTFOUND));

        Post post = postMapper.toPost(postCreationRequest);
        this.postRepository.save(user);
    }
}
