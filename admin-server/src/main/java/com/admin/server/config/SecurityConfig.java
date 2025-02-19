package com.admin.server.config;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

@Configuration
public class SecurityConfig {

    private final AdminServerProperties adminServer;

    public SecurityConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServer.getContextPath() + "/");

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
                .requestMatchers(this.adminServer.getContextPath() + "/login").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage(this.adminServer.getContextPath() + "/login")
                .successHandler(successHandler)
        )
        .logout(logout -> logout
                .logoutUrl(this.adminServer.getContextPath() + "/logout")
        )
        .httpBasic(Customizer.withDefaults())
        .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(
                        this.adminServer.getContextPath() + "/instances",
                        this.adminServer.getContextPath() + "/instances/*",
                        this.adminServer.getContextPath() + "/actuator/**"
                )
        )
        .rememberMe(rememberMe -> rememberMe
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(1209600)
        );

        return http.build();
    }
}
