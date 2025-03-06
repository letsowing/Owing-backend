package com.owing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import com.owing.common.util.JwtUtils;
import com.owing.config.securitypath.WebSecurityPath;
import com.owing.filter.JwtAuthenticationFilter;
import com.owing.filter.JwtExceptionFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils jwtUtils;

    @Value("${swagger.user}")
    private String swaggerUser;
    @Value("${swagger.password}")
    private String swaggerPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public UserDetailsService swaggerUserDetailsService() {
        User.UserBuilder userBuilder = User.builder()
                .passwordEncoder(pw -> passwordEncoder().encode(pw));
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                userBuilder.username(swaggerUser).password(swaggerPassword).roles("SWAGGER").build()
        );
        return manager;
    }

    @Bean
    public SecurityFilterChain swaggerSecurityFilterchain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(WebSecurityPath.SWAGGER_PATH.getPaths())
                .authorizeHttpRequests(httpRequest ->
                        httpRequest.anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatchers((auth) -> auth.requestMatchers(WebSecurityPath.REQUIRE_AUTH_PATH.getPaths()));
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Profile("local")
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(WebSecurityPath.SWAGGER_PATH.getPaths());
    }
}