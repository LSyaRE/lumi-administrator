package com.luminesway.concursoadminstrator.modules.auth.controllers;


import com.luminesway.concursoadminstrator.modules.auth.dtos.response.LoginResponse;
import com.luminesway.concursoadminstrator.shared.dtos.request.GenericRequest;
import com.luminesway.concursoadminstrator.shared.dtos.request.GenericRequestIp;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.AuthRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.RegisterRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.response.AuthResponse;
import com.luminesway.concursoadminstrator.modules.auth.services.facades.authentication.AuthenticationService;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@Log4j2
public class AuthController {
    @Qualifier("authenticationServiceImpl")
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericOnlyTextResponse> register(@RequestBody RegisterRequest req) {
        log.info("Registrando usuario: {}", req.getEmail());
        GenericOnlyTextResponse response = authenticationService.register(req);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<LoginResponse>> login(@Valid @RequestBody GenericRequest<AuthRequest> req,  HttpServletResponse res) {
        log.info("El usuario {} esta intentando entrar al sistema", req.getPayload().getEmail());
        GenericResponse<AuthResponse> response = authenticationService.login(req);
        res.addCookie(response.getData().getRefreshToken());
        return ResponseEntity
                .status(response.getCode())
                .body(GenericResponse.<LoginResponse>builder()
                        .message(response.getMessage())
                        .code(response.getCode())
                        .data(new LoginResponse(response.getData().getToken()))
                        .build()
                );
    }

    @GetMapping("/refresh")
    public ResponseEntity<GenericResponse<LoginResponse>> refresh(HttpServletRequest request) {
        GenericResponse<LoginResponse> response = authenticationService.refresh(request);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping("/logout")
    public ResponseEntity<GenericOnlyTextResponse> logout(HttpServletRequest headers, @RequestBody GenericRequestIp request) {
        GenericOnlyTextResponse response = authenticationService.logout(headers, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}