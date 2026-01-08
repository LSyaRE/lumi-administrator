package com.luminesway.concursoadminstrator.shared.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericOnlyTextResponse {
    private String message;
    private int status;
}
