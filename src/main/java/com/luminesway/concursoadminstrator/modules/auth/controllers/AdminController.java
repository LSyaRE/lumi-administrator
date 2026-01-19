package com.luminesway.concursoadminstrator.modules.auth.controllers;


import com.luminesway.concursoadminstrator.modules.auth.decorators.HasPermission;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.role.AssignRoleDTO;
import com.luminesway.concursoadminstrator.modules.auth.services.facades.admin.AdminServiceFacade;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.role.RoleDTO;
import com.luminesway.concursoadminstrator.modules.auth.entities.Permission;
import com.luminesway.concursoadminstrator.modules.auth.entities.Role;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericPaginationResponse;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@Log4j2
public class AdminController {
    private final AdminServiceFacade adminService;

    public AdminController(@Qualifier("adminServiceImpl") AdminServiceFacade adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/roles")
    @HasPermission("roles:find_all")
    public ResponseEntity<GenericPaginationResponse<RoleDTO>> getRoles(@PageableDefault(size = 100) Pageable pageable) {
        GenericPaginationResponse<RoleDTO> response = adminService.findAllRoles(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @PostMapping("/roles")
    @HasPermission("roles:create")
    public ResponseEntity<GenericResponse<Role>> createRole(@RequestBody Role role) {
        log.info("Creando rol: {}", role.toString());
        GenericResponse<Role> createRole = adminService.createRole(role);
        log.info("Devolviendo respuesta: {}", createRole.toString());
        return ResponseEntity
                .status(createRole.getStatus())
                .body(createRole);
    }

    @PostMapping("/permissions")
    @HasPermission("permissions:create")
    public ResponseEntity<?> createPermission(@RequestBody Permission permission) {
        log.info("Creando permiso: {}", permission.toString());
        GenericResponse<Permission> createdPermission = adminService.createPermission(permission);
        log.info("Devolviendo respuesta: {}", createdPermission.toString());
        return ResponseEntity
                .status(createdPermission.getStatus())
                .body(createdPermission);
    }

    @PostMapping("/roles/{roleName}/permissions")
    @HasPermission("roles:assign_permission")
    public ResponseEntity<GenericOnlyTextResponse> addPermissionToRole(@PathVariable String roleName, @RequestParam String permissionName) {
        log.info("Asignando permiso {} al rol {}", permissionName, roleName);
        GenericOnlyTextResponse response = adminService.addPermissionToRole(roleName, permissionName);
        log.info("Devolviendo respuesta: {}", response.toString());
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PostMapping("/users/{userId}/roles")
    @HasPermission("roles:find_user")
    public ResponseEntity<GenericOnlyTextResponse> addRoleToUser(@PathVariable UUID userId, @RequestBody AssignRoleDTO roleName) {
        log.info("Asignando rol {} al usuario {}", roleName, userId);
        GenericOnlyTextResponse response = adminService.addRoleToUser(userId, roleName);
        log.info("Devolviendo respuesta: {}", response.toString());
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }
}