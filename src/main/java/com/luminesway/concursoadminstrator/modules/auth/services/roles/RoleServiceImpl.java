package com.luminesway.concursoadminstrator.modules.auth.services.roles;

import ec.yavirac.yavigestion.modules.auth.services.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    @Qualifier("userServiceImpl")
    private final UserService userService;

    public RoleServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void hasAuthorization(String permission) {

    }

    @Override
    public void hasRole(String role) {

    }
}
