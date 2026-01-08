package com.luminesway.concursoadminstrator.modules.auth.dtos.request.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
}
