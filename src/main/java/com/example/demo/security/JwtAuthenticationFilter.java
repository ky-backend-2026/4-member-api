package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public  JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException{

        String bearerToken = request.getHeader("Authorization");

        if(bearerToken !=null && bearerToken.startsWith("Bearer ")){

            String token = bearerToken.substring(7);

            if(jwtTokenProvider.validateToken(token)){
                Long memberId = jwtTokenProvider.getMemberId(token);

                String role = jwtTokenProvider.getRole(token);

                UsernamePasswordAuthenticationToken authentication=
                        new UsernamePasswordAuthenticationToken(
                                memberId,
                                null,
                                java.util.List.of(
                                        new org.springframework.security.core.authority.SimpleGrantedAuthority(role)
                                )
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
