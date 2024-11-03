package com.wedsite.zuong2004.service;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.UserRequest;
import com.wedsite.zuong2004.dto.request.UserRequestUpdate;
import com.wedsite.zuong2004.dto.response.UserResponse;
import com.wedsite.zuong2004.enity.Role;
import com.wedsite.zuong2004.enity.User;
import com.wedsite.zuong2004.enums.RolePlay;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.mapper.UserMapper;
import com.wedsite.zuong2004.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_INVALID);
        }
        if (request.getFacebookAccountId() == 0 && request.getGoogleAccountId() == 0) {
            String password = request.getPassword();
            request.setPassword(password);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = new Role();
        role.setId(1L);
        role.setName(RolePlay.USER.name());
        user.setRole(role);
        user.setActive(true);
        return userMapper.mapToUserResponse(userRepository.save(user));
    }

    public UserResponse getMyinfo() {
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        log.info("ROLE:" + context.getAuthentication().getAuthorities());
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_USER));
        return userMapper.mapToUserResponse(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapToUserResponse).toList();
    }

    @PostAuthorize("returnObject.phoneNumber == authentication.name || hasRole('ADMIN')")
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        return userMapper.mapToUserResponse(user);
    }

    @PostAuthorize("returnObject.phoneNumber == authentication.name")
    public UserResponse updateUser(UserRequestUpdate request, Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));

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
        var context = SecurityContextHolder.getContext();
        String phoneNumber = context.getAuthentication().getName();
        if (!user.getPhoneNumber().equals(phoneNumber) &&
                context.getAuthentication().getAuthorities().stream()
                        .noneMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AppException(ErrorCode.NOT_USER);
        }
        if (user.isActive() == false) {
            throw new AppException(ErrorCode.INVALID_ID);
        }
        user.setActive(false);
        userRepository.save(user);
    }
}
