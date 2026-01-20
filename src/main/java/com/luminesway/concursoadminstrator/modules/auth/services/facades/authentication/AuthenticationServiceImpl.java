package com.luminesway.concursoadminstrator.modules.auth.services.facades.authentication;


import com.luminesway.concursoadminstrator.modules.auth.dtos.response.LoginResponse;
import com.luminesway.concursoadminstrator.modules.auth.entities.RefreshTokens;
import com.luminesway.concursoadminstrator.modules.auth.providers.jwt.JwtProviderImpl;
import com.luminesway.concursoadminstrator.modules.auth.services.bo.refreshtoken.RefreshTokenBO;
import com.luminesway.concursoadminstrator.modules.auth.services.database.refreshtoken.RefreshTokenService;
import com.luminesway.concursoadminstrator.shared.consts.EnglishConst;
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
import com.luminesway.concursoadminstrator.shared.utils.AesCryptoService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final AesCryptoService crypto;
    private final RefreshTokenBO refreshTokenBO;


    public AuthenticationServiceImpl(UserService userService, BCryptPasswordEncoder passwordEncoder, PersonService personService, JwtProvider jwtProvider, RefreshTokenService refreshTokenService, AesCryptoService crypto, RefreshTokenBO refreshTokenBO) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.personService = personService;
        this.jwtProvider = jwtProvider;

        this.refreshTokenService = refreshTokenService;
        this.crypto = crypto;
        this.refreshTokenBO = refreshTokenBO;
    }

    @Override
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
                .status(EnglishConst.ACTIVE)
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

            List<RefreshTokens> searchedRefresh = refreshTokenService.findAllByIpUserAndUser(req.getIpCreacion(), user);

            log.info("Generando token para el usuario {}", user.getEmail());
            String token = jwtProvider.generateAccessToken(user.getId());
            String refreshToken = chooseRefreshToken(searchedRefresh, user, req.getIpCreacion());

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/auth/refresh");
            cookie.setMaxAge(Math.toIntExact(JwtProviderImpl.REFRESH_EXPIRATION));

            log.info("Token generado");
            log.info("Usuario autenticado: {}", user.getEmail());
            return GenericResponse.<AuthResponse>builder()
                    .code(200)
                    .data(new AuthResponse(token, cookie))
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
    @Transactional
    public GenericResponse<LoginResponse> refresh(HttpServletRequest token) {
        Optional<String> refreshToken = refreshTokenBO.obtainRefreshTokenFromCookie(token);
        if (refreshToken.isEmpty()) {
            return GenericResponse
                    .<LoginResponse>builder()
                    .message("La sesion es invalida ")
                    .code(401)
                    .build();
        }

        String validatedRefreshToken = refreshToken.get();
        UUID userId = jwtProvider.getUserIdFromRefreshToken(validatedRefreshToken);
        User user = userService.loadUserById(userId);
        List<RefreshTokens> rtTokens = refreshTokenService.findAllByUser(user);

        if(rtTokens.isEmpty()) {
            return GenericResponse
                    .<LoginResponse>builder()
                    .message("La sesion es invalida ")
                    .code(401)
                    .build();
        }

        if(!jwtProvider.validateRefreshToken(validatedRefreshToken)) {

            List<RefreshTokens> tokens = rtTokens.stream()
                    .filter(rt -> {
                        String decryptedToken = crypto.decrypt(rt.getToken());
                        return decryptedToken.equals(validatedRefreshToken);
                    }
                    ).toList();

            tokens.forEach(rt -> {
                rt.setStatus(EnglishConst.INACTIVE);
            });

            refreshTokenService.saveAll(tokens);

            return GenericResponse
                    .<LoginResponse>builder()
                    .message("La sesion es invalida ")
                    .code(401)
                    .build();
        }

        String newToken = jwtProvider.generateAccessToken(userId);

        return GenericResponse
                .<LoginResponse>builder()
                .code(200)
                .message("Token Generado exitosamente")
                .data(new LoginResponse(newToken))
                .build();
    }

    @Override
    public GenericOnlyTextResponse logout(HttpServletRequest headers, GenericRequestIp request) {
        Optional<String> headerRefresh = refreshTokenBO.obtainRefreshTokenFromCookie(headers);
        if (headerRefresh.isEmpty()) {
            return GenericOnlyTextResponse
                    .builder()
                    .message("La sesion es invalida ")
                    .status(401)
                    .build();
        }

        String validatedRefreshToken = headerRefresh.get();
        UUID userId = jwtProvider.getUserIdFromRefreshToken(validatedRefreshToken);
        User user = userService.loadUserById(userId);

        List<RefreshTokens> refreshTokens = refreshTokenService
                .findAllByIpUserAndUser(request.getIpCreacion(), user);

        refreshTokens.forEach(refreshToken -> {
            refreshToken.setStatus(EnglishConst.INACTIVE);
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


    private String chooseRefreshToken(List<RefreshTokens> tokens, User user, String ip) {
        String refreshToken;

        if(tokens.isEmpty()) {
            refreshToken = jwtProvider.generateRefreshToken(user.getId());
            RefreshTokens saveRefresh = new RefreshTokens();
            saveRefresh.setToken(crypto.encrypt(refreshToken));
            saveRefresh.setUser(user);
            saveRefresh.setIpUser(ip);
            refreshTokenService.save(saveRefresh);
            return refreshToken;
        }

        return crypto.decrypt(tokens.get(0).getToken());
    }


}
