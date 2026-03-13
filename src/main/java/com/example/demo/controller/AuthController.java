package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public MemberResponse login(@RequestBody LoginRequest request, HttpSession session){
        return authService.login(request,session);
    }
    @PostMapping("/logout")
    public void logout(HttpSession session){
        authService.logout(session);
    }
    @GetMapping("/me")
    public MemberResponse memberResponse(HttpSession session){
        return authService.getLoginMember(session);
    }



}
