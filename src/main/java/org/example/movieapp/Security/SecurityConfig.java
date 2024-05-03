package org.example.movieapp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/login", "/register","actor/{id}","/movie/{id}","webjars/**","/css/**", "/img/**", "script/**", "/actor", "/movie", "/movie/country/", "movie/genre/").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> {
                    login.loginPage("/login")
                            .loginProcessingUrl("/login")
                            .failureUrl("/login?error")
                            .defaultSuccessUrl("/movie", true);
                });
        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
