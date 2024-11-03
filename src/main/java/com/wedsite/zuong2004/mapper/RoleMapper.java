package com.wedsite.zuong2004.mapper;

import com.wedsite.zuong2004.dto.request.RoleRequest;
import com.wedsite.zuong2004.dto.response.RoleResponse;
import com.wedsite.zuong2004.enity.Role;

public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse mapToRoleResponse(Role role);
}
