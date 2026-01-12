package com.luminesway.concursoadminstrator.modules.auth.services.bo.permission;


import com.luminesway.concursoadminstrator.modules.auth.entities.Permission;
import com.luminesway.concursoadminstrator.modules.auth.entities.Role;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service("permissionService")
public class PermissionServiceBO {

    public boolean hasPermission(String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        return hasEffectivePermission(user, permission);
    }


    public Set<String> getEffectivePermissions(User user) {
        if (user == null) return Set.of();
        boolean isAdmin = user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(rn -> rn.equalsIgnoreCase("ROLE_ADMIN") || rn.equalsIgnoreCase("admin"));

        if (isAdmin) {
            return Set.of("*");
        }

        return user.getRoles().stream()
                .flatMap(r -> {
                    Set<Permission> perms = r.getPermissions();
                    if (perms == null) return java.util.stream.Stream.empty();
                    return perms.stream();
                })
                .map(Permission::getName)
                .collect(Collectors.toSet());
    }

    public boolean hasEffectivePermission(User user, String permission) {
        Set<String> effective = getEffectivePermissions(user);
        if (effective.contains("*")) return true;
        return effective.contains(permission);
    }

    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .anyMatch(rn -> rn.equalsIgnoreCase("ROLE_ADMIN") || rn.equalsIgnoreCase("admin"));
    }
}
