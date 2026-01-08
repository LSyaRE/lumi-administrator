package com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth;

import com.luminesway.concursoadminstrator.modules.auth.dtos.request.PersonDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterRequest {
    private PersonDto person;
    @Email(message = "Debes colocar un email valido")
    @NotEmpty
    @NotNull
    @NotBlank
    private String email;
    @NotEmpty
    @NotNull
    @NotBlank
    private String password;
    @NotEmpty
    @NotNull
    @NotBlank
    private String confirmPassword;
}
