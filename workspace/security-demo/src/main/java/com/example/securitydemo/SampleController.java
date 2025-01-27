package com.example.securitydemo;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.DelegatingFilterProxy;

@RestController
public class SampleController {


    @GetMapping("/hello")
    public  String sayHello() {
        return "Hello User!!!";
    }

    @GetMapping("/admin")
    public  String sayHelloAdmin() {
        return "Hello Admin!!!";
    }
}
