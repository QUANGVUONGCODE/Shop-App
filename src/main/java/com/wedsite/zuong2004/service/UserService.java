package com.wedsite.zuong2004.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.UserRequest;
import com.wedsite.zuong2004.dto.request.UserRequestUpdate;
import com.wedsite.zuong2004.dto.response.UserResponse;
import com.wedsite.zuong2004.enity.Role;
import com.wedsite.zuong2004.enity.User;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.mapper.UserMapper;
import com.wedsite.zuong2004.repository.RoleRepository;
import com.wedsite.zuong2004.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_INVALID);
        }
        if (request.getFacebookAccountId() == 0 && request.getGoogleAccountId() == 0) {
            String password = request.getPassword();
            request.setPassword(password);
        }

        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(
                () -> new AppException(ErrorCode.ROLE_ID_REQUIRED));

        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setActive(true);
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        return userMapper.mapToUserResponse(user);
    }

    public UserResponse updateUser(UserRequestUpdate request, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

        if (roleRepository.existsById(request.getRoleId())) {
            throw new AppException(ErrorCode.ROLE_ID_REQUIRED);
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_INVALID);
        }

        if (request.getFacebookAccountId() == 0 && request.getGoogleAccountId() == 0) {
            String password = request.getPassword();
            request.setPassword(password);
        }
        userMapper.updateUser(request, user);
        userRepository.save(user);

        return userMapper.mapToUserResponse(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        user.setActive(false);
        userRepository.save(user);
    }

}
