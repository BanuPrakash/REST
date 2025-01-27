package com.example.securitydemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager users() {
        return  new InMemoryUserDetailsManager(User.withUsername("rita")
                .password("{noop}secret").authorities("ROLE_READ").build(),
                User.withUsername("sam")
                        .password("{noop}secret").authorities("ROLE_ADMIN", "ROLE_READ").build()
        ) ;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests( requests -> requests.requestMatchers("/hello")
                .hasAnyRole("READ", "ADMIN")
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/").permitAll())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
