package com.ntd.socialnetwork.post.mapper;

import com.ntd.socialnetwork.post.dto.request.PostCreationRequest;
import com.ntd.socialnetwork.post.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toPost(PostCreationRequest postCreationRequest);
}
