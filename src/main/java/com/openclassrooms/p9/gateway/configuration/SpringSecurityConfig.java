package com.openclassrooms.p9.gateway.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SpringSecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Création des utilisateurs en mémoire
        UserDetails user1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN") // Définit le rôle de l'utilisateur
                .build();

        UserDetails user2 = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    /**
     * Sets up the required filters to restrain accessibility of endpoints to registered users.
     * Configures also the session parameters: only 1 session at a time, stores cookies only if needed
     *
     * @param http web based security configuration
     * @throws Exception of every kind
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/home.html").permitAll();
                    auth.requestMatchers("/user/*").hasRole("ADMIN");
                    auth.requestMatchers("/403").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .formLogin(form -> form
                        .defaultSuccessUrl("/loginsuccessful", true))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/403.html")
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Encoder de mots de passe
        return new BCryptPasswordEncoder();
    }
}
