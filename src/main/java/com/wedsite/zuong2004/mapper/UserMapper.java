package com.wedsite.zuong2004.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.wedsite.zuong2004.dto.request.UserRequest;
import com.wedsite.zuong2004.dto.request.UserRequestUpdate;
import com.wedsite.zuong2004.dto.response.UserResponse;
import com.wedsite.zuong2004.enity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest request);

    UserResponse mapToUserResponse(User user);

    void updateUser(UserRequestUpdate request, @MappingTarget User user);
}
