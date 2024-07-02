package kr.gyk.adobby.unsolved_backend.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.gyk.adobby.unsolved_backend.jwt.JwtAuthenticationFilter;
import kr.gyk.adobby.unsolved_backend.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(c -> {
                            CorsConfigurationSource source = request -> {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(List.of("*"));
                                config.setAllowedMethods(List.of("*"));
                                return config;
                            };
                            c.configurationSource(source);
                        }
                )
                .sessionManagement(configurer->configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/user/logout").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/user").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/user/token").permitAll()
                                .requestMatchers(HttpMethod.POST, "/problem").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/problem").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/problem").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/tag").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/tag").hasRole("ADMIN")
                                .anyRequest().denyAll()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        AuthenticationManager->AuthenticationManager
                                .authenticationEntryPoint(
                                        new AuthenticationEntryPoint() {
                                            @Override
                                            public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
                                                response.setStatus(401);
                                                response.setCharacterEncoding("utf-8");
                                                response.setContentType("text/html; charset=UTF-8");
                                                response.getWriter().write("401 Unauthorized");
                                            }
                                        }
                                )
                                .accessDeniedHandler(
                                        new AccessDeniedHandler() {
                                            @Override
                                            public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                                response.setStatus(403);
                                                response.setCharacterEncoding("utf-8");
                                                response.setContentType("text/html; charset=UTF-8");
                                                response.getWriter().write("403 Forbidden");
                                            }
                                        }
                                )
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}