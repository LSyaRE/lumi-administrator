package com.luminesway.concursoadminstrator.modules.auth.services.facades.authentication;


import com.luminesway.concursoadminstrator.modules.auth.entities.RefreshTokens;
import com.luminesway.concursoadminstrator.modules.auth.services.database.refreshtoken.RefreshTokenService;
import com.luminesway.concursoadminstrator.modules.core.consts.StatusConst;
import com.luminesway.concursoadminstrator.shared.dtos.request.GenericRequest;
import com.luminesway.concursoadminstrator.shared.dtos.request.GenericRequestIp;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericResponse;
import com.luminesway.concursoadminstrator.shared.dtos.response.GenericOnlyTextResponse;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.PersonDto;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.AuthRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.request.auth.RegisterRequest;
import com.luminesway.concursoadminstrator.modules.auth.dtos.response.AuthResponse;
import com.luminesway.concursoadminstrator.modules.auth.entities.Person;
import com.luminesway.concursoadminstrator.modules.auth.entities.User;
import com.luminesway.concursoadminstrator.modules.auth.providers.jwt.JwtProvider;
import com.luminesway.concursoadminstrator.modules.auth.services.database.person.PersonService;
import com.luminesway.concursoadminstrator.modules.auth.services.database.user.UserService;
import com.luminesway.concursoadminstrator.shared.utils.Mapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Qualifier("userServiceImpl")
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Qualifier("personServiceImpl")
    private final PersonService personService;

    @Qualifier("jwtProviderImpl")
    private final JwtProvider jwtProvider;

    @Qualifier("refreshTokenServiceImpl")
    private final RefreshTokenService refreshTokenService;


    public AuthenticationServiceImpl(UserService userService, BCryptPasswordEncoder passwordEncoder, PersonService personService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.personService = personService;
        this.jwtProvider = jwtProvider;

        this.refreshTokenService = refreshTokenService;
    }

    public GenericOnlyTextResponse register(RegisterRequest req) {
        if (userService.findByEmail(req.getEmail()).isPresent()) {
            return GenericOnlyTextResponse.builder()
                            .message("email ya resgistrado")
                            .status(HttpStatus.BAD_REQUEST.value()).build();
        }

        if(!req.getPassword().equals(req.getConfirmPassword())) {
            return GenericOnlyTextResponse.builder()
                    .message("Las contraseñas no coinciden")
                    .status(HttpStatus.BAD_REQUEST.value()).build();
        }


        PersonDto personDto = req.getPerson();
        Person person = new Person();
        person.setName(personDto.getName());
        person.setLastname(personDto.getLastname());
        Person createdPerson = personService.save(person);

        User user = User.builder()
                .email(req.getEmail())
                .passwordHash(passwordEncoder.encode(req.getPassword()))
                .person(createdPerson)
                .status(StatusConst.ACTIVE)
                .build();
        userService.save(user);


        return GenericOnlyTextResponse.builder()
                        .message("Registrado")
                        .status(HttpStatus.CREATED.value()).build();
    }

    public GenericResponse<AuthResponse> login(GenericRequest<AuthRequest> req) {
        try {
            AuthRequest payload = req.getPayload();
            Optional<User> opt = userService.findByEmail(payload.getEmail());
            if (opt.isEmpty()) {
                log.warn("Credenciales incorrectas");
                return GenericResponse.<AuthResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message("Credenciales inválidas").build();
            }
            User user = opt.get();
            if (!passwordEncoder.matches(payload.getPassword(), user.getPasswordHash())) {
                log.warn("Credenciales incorrectas");

                return GenericResponse.<AuthResponse>builder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message("Credenciales inválidas")
                        .build();
            }

            log.info("Generando token para el usuario {}", user.getEmail());
            String token = jwtProvider.generateAccessToken(user.getId());
            String refreshToken = jwtProvider.generateRefreshToken(user.getId());
            RefreshTokens saveRefresh = new RefreshTokens();
            saveRefresh.setToken(passwordEncoder.encode(refreshToken));
            saveRefresh.setUser(user);
            saveRefresh.setIpUser(req.getIpCreacion());
            refreshTokenService.save(saveRefresh);

            log.info("Token generado");
            log.info("Usuario autenticado: {}", user.getEmail());
            return GenericResponse.<AuthResponse>builder()
                    .code(200)
                    .data(new AuthResponse(token, refreshToken))
                    .build();
        } catch (Exception e) {
            log.error("Error al generar token", e);
            return GenericResponse.<AuthResponse>builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Error al generar token").build();
        }
    }

    @Override
    public Object changePassword(String oldPassword, String newPassword) {
        return null;
    }

    @Override
    public GenericOnlyTextResponse logout(UUID id, GenericRequestIp request) {
        User user = userService.loadUserById(id);
        List<RefreshTokens> refreshTokens = refreshTokenService
                .findAllByIpUserAndUser(request.getIpCreacion(), user);

        refreshTokens.forEach(refreshToken -> {
            refreshToken.setStatus(StatusConst.INACTIVE);
        });

        refreshTokenService.saveAll(refreshTokens);

        return GenericOnlyTextResponse.builder()
                .message("Se ha cerrado sesion correctamente")
                .status(HttpStatus.OK.value())
                .build();
    }

    @Override
    public boolean isRefreshTokenValid(String refreshToken) {
        return true;
    }

}
