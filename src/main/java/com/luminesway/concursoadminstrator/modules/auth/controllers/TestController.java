package com.luminesway.concursoadminstrator.modules.auth.controllers;

import com.luminesway.concursoadminstrator.modules.auth.consts.RoleConsts;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello-1")
    @PreAuthorize("hasAnyRole(@roles.ADMIN)")
    public String hello() {

        return "Hello World Admin";
    }

    @GetMapping("/hello-2")
    @PreAuthorize("hasAnyRole(@roles.USER, @roles.ADMIN)")
    public String hello2() {
        return "Hello World User";
    }

}
