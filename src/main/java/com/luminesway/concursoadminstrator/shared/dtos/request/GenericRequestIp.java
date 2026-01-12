package com.luminesway.concursoadminstrator.shared.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericRequestIp {
    @NotNull( message = "La ipCreacion no puede ser nula")
    @NotEmpty( message = "La ipCreacion no debe estar vacia")
    private String ipCreacion;
}
