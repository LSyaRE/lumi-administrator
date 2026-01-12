package com.luminesway.concursoadminstrator.modules.auth.services.bo.authentication;

import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class AuthenticationBO {

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationBO(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public boolean validateCredentials(Optional<User> user) {


        return true;
    }

    public String encode(String text) {
        return passwordEncoder.encode(text);
    }

    public boolean matches(String text, String encoded) {
        return passwordEncoder.matches(text, encoded);
    }
}
