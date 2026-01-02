package com.luminesway.concursoadminstrator.config;

import com.luminesway.concursoadminstrator.modules.auth.consts.RoleConsts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationConverter  jwtAuthenticationConverter;

    public SecurityConfig(JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    /**
     * Configures the security filter chain for the application. This method specifies security settings,
     * such as disabling CSRF, configuring role-based access to requests, enabling JWT-based OAuth2 resource
     * server authentication, and setting session management to stateless.
     *
     * @param http the HttpSecurity object used to configure security filters for HTTP requests.
     * @return a fully built SecurityFilterChain object that includes the configured security settings.
     * @throws Exception if an error occurs while configuring the security filter chain.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpReq -> httpReq.requestMatchers("/**").hasRole(RoleConsts.ADMIN).anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
