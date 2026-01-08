package com.luminesway.concursoadminstrator.modules.auth.services.admin;


import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericPaginationResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.role.AssignRoleDTO;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.role.RoleDTO;
import com.luminesway.concursoadminstrator.modules.auth.entities.Permission;
import com.luminesway.concursoadminstrator.modules.auth.entities.Role;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import com.luminesway.concursoadminstrator.modules.auth.repositories.PermissionRepository;
import com.luminesway.concursoadminstrator.modules.auth.repositories.RoleRepository;
import com.luminesway.concursoadminstrator.modules.auth.repositories.UserRepository;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final UserRepository userRepo;


    public AdminServiceImpl(RoleRepository roleRepo, PermissionRepository permissionRepo, UserRepository userRepo) {
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
        this.userRepo = userRepo;
    }

    @Override
    public GenericPaginationResponse<RoleDTO> findAllRoles(Pageable pageable) {
        Page<Role> roles = roleRepo.findAll(pageable);
        List<RoleDTO> transformedRoles = roles.getContent().stream()
                .map(role -> RoleDTO
                        .builder()
                        .id(role.getId())
                        .description(role.getDescription())
                        .name(role.getName())
                        .status(role.getStatus())
                        .build()).toList();

        return ResponseEntity.ok(GenericPaginationResponse
                .<RoleDTO>builder()
                .currentPage(pageable.getPageNumber())
                .data(transformedRoles)
                .totalPages(roles.getTotalPages())
                .pageSize(pageable.getPageSize())
                .totalElements(roles.getTotalElements())
                .status(200)
                .build()).getBody();
    }

    public GenericResponse<Role> createRole(Role role) {
        if (roleRepo.findByName(role.getName()).isPresent()) return GenericResponse.<Role>builder()
                .message("El rol que se desea crear ya existe")
                .status(400)
                .build();
        Role saved = roleRepo.save(role);
        return GenericResponse.<Role>builder()
                .status(201)
                .message("Rol creado correctamente")
                .data(saved).build();
    }
    
    public GenericResponse<Permission> createPermission(Permission permission) {
        if (permissionRepo.findByName(permission.getName()).isPresent()) return GenericResponse.<Permission>builder()
                .status(400)
                .message("El permiso que desea crear ya existe")
                .build();
        Permission saved = permissionRepo.save(permission);
        return GenericResponse.<Permission>builder()
                .status(201)
                .message("Permiso creado correctamente")
                .data(saved)
                .build();
    }


    public GenericOnlyTextResponse addPermissionToRole(String roleName, String permissionName) {

        Optional<Role> ro = roleRepo.findByName(roleName);
        if (ro.isEmpty()) return GenericOnlyTextResponse.builder().status(404).message("Rol no encontrado").build();
        Role role = ro.get();

        Permission perm = permissionRepo.findByName(permissionName)
                .orElseGet(() -> permissionRepo.save(Permission.builder().name(permissionName).build()));
        role.getPermissions().add(perm);
        roleRepo.save(role);
        return GenericOnlyTextResponse.builder()
                .message("Permiso añadido")
                .status(200)
                .build();
    }

    public GenericOnlyTextResponse addRoleToUser(Long userId, AssignRoleDTO roles) {
        Optional<User> ou = userRepo.findById(userId);
        if (ou.isEmpty()) return GenericOnlyTextResponse.builder().status(404).message("Usuario no encontrado").build();
        List<Role> foundRoles = roleRepo.findAllById(roles.getRoleIds());


        User u = ou.get();
        u.setRoles(new HashSet<>(foundRoles));
        userRepo.save(u);
        return GenericOnlyTextResponse.builder().status(200).message("Rol asignado").build();
    }
}
