package com.luminesway.concursoadminstrator.modules.auth.services.roles;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    public void hasAuthorization(String permission);
    public void hasRole(String role);

}
