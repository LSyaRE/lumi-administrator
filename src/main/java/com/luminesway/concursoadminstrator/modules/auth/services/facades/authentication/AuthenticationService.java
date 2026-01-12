package com.luminesway.concursoadminstrator.modules.auth.services.facades.authentication;


import com.luminesway.concursoadminstrator.shared.dtos.request.GenericRequest;
import com.luminesway.concursoadminstrator.shared.dtos.request.GenericRequestIp;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.AuthRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.RegisterRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.response.AuthResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthenticationService {
    GenericResponse<AuthResponse> login(GenericRequest<AuthRequest> request);
    GenericOnlyTextResponse register(RegisterRequest request);
    boolean isRefreshTokenValid(String refreshToken);
    Object changePassword(String oldPassword, String newPassword);
    GenericOnlyTextResponse logout(UUID id, GenericRequestIp request);
}
