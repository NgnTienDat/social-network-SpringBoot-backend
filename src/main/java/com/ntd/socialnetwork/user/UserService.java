package com.ntd.socialnetwork.user;

import com.ntd.socialnetwork.user.dto.request.UserCreationRequest;
import com.ntd.socialnetwork.user.dto.request.UserUpdateRequest;
import com.ntd.socialnetwork.user.dto.response.UserResponse;
import com.ntd.socialnetwork.user.exception.ErrorCode;
import com.ntd.socialnetwork.user.exception.UserNotFoundException;
import com.ntd.socialnetwork.user.exception.UsernameAlreadyExistsException;
import com.ntd.socialnetwork.user.mapper.UserMapper;
import com.ntd.socialnetwork.user.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public void createUser(UserCreationRequest userCreationRequest) {

        if (this.userRepository.existsUserByUsername(userCreationRequest.getUsername())) {
            throw new UsernameAlreadyExistsException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = userMapper.toUser(userCreationRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userCreationRequest.getPassword()));

        userRepository.save(user);
    }

    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User user = this.userRepository.findByUsername(userUpdateRequest.getUsername());
        if (user == null) {
            throw new UserNotFoundException(ErrorCode.USER_NOTFOUND);
        }

        userMapper.updateUser(user, userUpdateRequest);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserResponse getUserByUsername(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOTFOUND)));
    }

}
