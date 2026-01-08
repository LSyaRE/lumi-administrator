package com.luminesway.concursoadminstrator.modules.auth.dtos.request;


import ec.yavirac.yavigestion.modules.administration.enums.BloodType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PersonDto {
    private Long id;
    private String name;
    private String lastname;
    private String dni;
    private String phonenumber;
    private String address;
    private BloodType bloodtype;
    private String gender;
    private LocalDate birthdate;
    private String email;
    private String status;
}
