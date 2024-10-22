package com.wedsite.zuong2004.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wedsite.zuong2004.dto.request.ApiResponse;
import com.wedsite.zuong2004.dto.request.RoleRequest;
import com.wedsite.zuong2004.dto.response.RoleResponse;
import com.wedsite.zuong2004.service.RoleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;

@RestController
@RequestMapping("api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RoleResponse> updateRole(@RequestBody @Valid RoleRequest request, @PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.updateRole(request, id))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<RoleResponse> getRoleById(@PathVariable Long id) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRoleById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.<String>builder()
                .result("Deleted successfully")
                .build();
    }

}
