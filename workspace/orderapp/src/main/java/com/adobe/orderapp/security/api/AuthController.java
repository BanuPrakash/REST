package com.adobe.orderapp.security.api;


import com.adobe.orderapp.security.dto.SignInRequest;
import com.adobe.orderapp.security.dto.SignUpRequest;
import com.adobe.orderapp.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private  final AuthenticationService service;

    @PostMapping("/register")
    public String register(@RequestBody SignUpRequest request) {
        System.out.println("Entered!!!");
        return  service.signup(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody SignInRequest request) {
        return  service.signIn(request);
    }
}
