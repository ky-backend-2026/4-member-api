package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.dto.TokenResponse;
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
    public TokenResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @PostMapping("/reissue")
    public TokenResponse reissue(@RequestHeader("Refresh-Token") String refreshToken){
        return  authService.reissue(refreshToken);
    }




}
