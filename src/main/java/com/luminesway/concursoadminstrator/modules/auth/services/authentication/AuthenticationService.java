package com.luminesway.concursoadminstrator.modules.auth.services.authentication;


import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.AuthRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.RegisterRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.response.AuthResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    public GenericResponse<AuthResponse> login(AuthRequest request);
    public GenericOnlyTextResponse register(RegisterRequest request);

    public Object changePassword(String oldPassword, String newPassword);
    public Object logout(String token);
}
