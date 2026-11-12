package com.jcaa.usersmanagement.infrastructure.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

  private static final String ROLE_ADMIN = "ADMIN";
  private static final String ROLE_REVIEWER = "REVIEWER";
  private static final String AUTH_LOGIN_PATH = "/api/auth/login";
  private static final String USERS_PATH = "/api/users";
  private static final String USERS_DETAIL_PATH = "/api/users/**";
  private static final String COMPUTERS_PATH = "/api/computers";
  private static final String COMPUTERS_DETAIL_PATH = "/api/computers/**";
  private static final String[] OPEN_API_PATHS = {
          "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**"
  };

  @Bean
  public SecurityFilterChain securityFilterChain(
          final HttpSecurity http,
          final JwtAuthenticationFilter jwtAuthenticationFilter,
          final RestAuthenticationEntryPoint authenticationEntryPoint,
          final RestAccessDeniedHandler accessDeniedHandler)
          throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .sessionManagement(
                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(
                    handling ->
                            handling
                                    .authenticationEntryPoint(authenticationEntryPoint)
                                    .accessDeniedHandler(accessDeniedHandler))
            .authorizeHttpRequests(
                    authorization ->
                            authorization
                                    .requestMatchers(HttpMethod.GET, "/health")
                                    .permitAll()
                                    .requestMatchers(AUTH_LOGIN_PATH)
                                    .permitAll()
                                    .requestMatchers(OPEN_API_PATHS)
                                    .permitAll()
                                    .requestMatchers(HttpMethod.POST, USERS_PATH)
                                    .permitAll()
                                    .requestMatchers(HttpMethod.GET, USERS_PATH, USERS_DETAIL_PATH)
                                    .hasAnyRole(ROLE_ADMIN, ROLE_REVIEWER)
                                    .requestMatchers(HttpMethod.PUT, USERS_DETAIL_PATH)
                                    .hasRole(ROLE_ADMIN)
                                    .requestMatchers(HttpMethod.DELETE, USERS_DETAIL_PATH)
                                    .hasRole(ROLE_ADMIN)
                                    .requestMatchers(HttpMethod.GET, COMPUTERS_PATH, COMPUTERS_DETAIL_PATH)
                                    .hasAnyRole(ROLE_ADMIN, ROLE_REVIEWER)
                                    .requestMatchers(HttpMethod.POST, COMPUTERS_PATH)
                                    .hasRole(ROLE_ADMIN)
                                    .requestMatchers(HttpMethod.PUT, COMPUTERS_DETAIL_PATH)
                                    .hasRole(ROLE_ADMIN)
                                    .requestMatchers(HttpMethod.DELETE, COMPUTERS_DETAIL_PATH)
                                    .hasRole(ROLE_ADMIN)
                                    .anyRequest()
                                    .authenticated())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}