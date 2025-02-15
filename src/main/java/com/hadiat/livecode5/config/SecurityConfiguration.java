package com.hadiat.livecode5.config;

import com.hadiat.livecode5.config.advisers.CustomAccessDeniedException;
import com.hadiat.livecode5.config.advisers.CustomAuthenticationEntryPoint;
import com.hadiat.livecode5.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final AdminSecretKeyFilter adminSecretKeyFilter;
    private final SuperAdminSecretKeyFilter superAdminSecretKeyFilter;

    @Autowired
    private CustomAccessDeniedException accessDeniedException;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**", "/api/admin/super-admin", "/api/admin").permitAll()
                .requestMatchers("/api/todos/**").hasAuthority(Role.USER.name())
                .requestMatchers("/api/admin/**", "/api/todos/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/api/admin/users/*/role", "/api/admin/**", "/api/todos/**").hasAuthority(Role.SUPER_ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(accessDeniedException)
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(adminSecretKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(superAdminSecretKeyFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                    .logoutUrl("/api/auth/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }
}
