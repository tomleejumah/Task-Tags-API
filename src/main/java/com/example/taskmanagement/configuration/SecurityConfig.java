package com.example.taskmanagement.configuration;

import com.example.taskmanagement.response.ResponseHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /**
 * Configures Spring Security for the application.
 *
 * @param http The HttpSecurity object to configure security settings.
 * @return A SecurityFilterChain object representing the configured security settings.
 * @throws Exception If an error occurs during security configuration.
 */
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Disable CSRF protection
    http.csrf(AbstractHttpConfigurer::disable)
            // Define access control rules
            .authorizeHttpRequests(auth -> auth
                    // Require authentication for /tasks and /tags endpoints
                    .requestMatchers("/tasks", "/tags").authenticated()
                    // Require authentication for /tasks/** and /tags/** endpoints
                    .requestMatchers("/tasks/**", "/tags/**").authenticated()
                    // Allow access to all other requests
                    .anyRequest().permitAll()
            )
            // Enable HTTP Basic authentication
            .httpBasic(Customizer.withDefaults());

    // Build and return the SecurityFilterChain
    return http.build();
}
        /**
 * Configures a UserDetailsService bean for authentication.
 *
 * This bean provides user details for authentication purposes. In this case,
 * an in-memory user is created with the username "username", password "password",
 * and the role "USER". The password is encoded using the BCryptPasswordEncoder.
 *
 * @return A UserDetailsService bean for authentication.
 */
@Bean
public UserDetailsService userDetailsService(){

    // Create a user with the specified username, password, and role
    UserDetails user = User.builder()
            .username("username")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

    // Return an InMemoryUserDetailsManager with the created user
    return new InMemoryUserDetailsManager(user);
}
    /**
 * Configures a PasswordEncoder bean for encoding passwords.
 *
 * This bean uses the BCryptPasswordEncoder to securely encode passwords. BCrypt is a
 * modern password hashing algorithm that generates a unique salt for each password and
 * applies a strong hashing algorithm to protect the passwords.
 *
 * @return A PasswordEncoder bean for encoding passwords.
 */
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
}

