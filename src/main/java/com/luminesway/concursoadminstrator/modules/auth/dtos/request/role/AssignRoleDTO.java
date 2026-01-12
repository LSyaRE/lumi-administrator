package com.luminesway.concursoadminstrator.modules.auth.dtos.request.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AssignRoleDTO {
    List<UUID> roleIds;
}
