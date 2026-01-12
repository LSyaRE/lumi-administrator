package com.luminesway.concursoadminstrator.modules.auth.services.facades.admin;


import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericPaginationResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.role.AssignRoleDTO;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.role.RoleDTO;
import com.luminesway.concursoadminstrator.modules.auth.entities.Permission;
import com.luminesway.concursoadminstrator.modules.auth.entities.Role;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AdminServiceFacade {
    GenericPaginationResponse<RoleDTO> findAllRoles(Pageable pageable);
    GenericResponse<Role> createRole(Role role);
    GenericResponse<Permission> createPermission(Permission permission);
    GenericOnlyTextResponse addPermissionToRole(String roleName, String permissionName);
    GenericOnlyTextResponse addRoleToUser(UUID userId, AssignRoleDTO roles);
}
