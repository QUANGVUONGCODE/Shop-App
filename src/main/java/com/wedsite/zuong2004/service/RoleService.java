package com.wedsite.zuong2004.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wedsite.zuong2004.dto.request.RoleRequest;
import com.wedsite.zuong2004.dto.response.RoleResponse;
import com.wedsite.zuong2004.enity.Role;
import com.wedsite.zuong2004.exception.AppException;
import com.wedsite.zuong2004.exception.ErrorCode;
import com.wedsite.zuong2004.mapper.RoleMapper;
import com.wedsite.zuong2004.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        Role role = roleMapper.toRole(request);
        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream().map(roleMapper::mapToRoleResponse).toList();
    }

    public RoleResponse getRoleById(Long id) {
        return roleMapper.mapToRoleResponse(roleRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public RoleResponse updateRole(RoleRequest request, Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        role.setName(request.getName());
        return roleMapper.mapToRoleResponse(roleRepository.save(role));
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new AppException(ErrorCode.INVALID_ID);
        }
        roleRepository.deleteById(id);
    }
}
