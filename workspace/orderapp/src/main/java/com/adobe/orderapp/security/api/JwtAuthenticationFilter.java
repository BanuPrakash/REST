package com.adobe.orderapp.security.api;


import com.adobe.orderapp.security.service.JwtService;
import com.adobe.orderapp.security.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt;
        String email;
        //||
        //                !StringUtils.startsWithIgnoreCase("authHeader", "Bearer")
        if(ObjectUtils.isEmpty(authHeader) ) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); // remove Bearer
        email = jwtService.extractUserName(jwt);
        System.out.println("Extracted " + email);
//        if(!StringUtils.hasText(email) &&
//                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.userDetailsService().loadUserByUsername(email);
            if(jwtService.isTokenValid(jwt, userDetails)) {
                System.out.println("Token is valid!!!");
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);
//            }
            filterChain.doFilter(request, response);
        }
    }
}
