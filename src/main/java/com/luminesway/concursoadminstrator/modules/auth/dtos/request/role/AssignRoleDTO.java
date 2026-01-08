package com.luminesway.concursoadminstrator.modules.auth.dtos.request.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AssignRoleDTO {
    List<Long> roleIds;
}
