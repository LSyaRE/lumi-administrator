package com.luminesway.concursoadminstrator.modules.auth.dtos.request.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class RoleDTO {
    private UUID id;
    private String name;
    private String description;
    private String status;
}
