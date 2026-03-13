package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    private String refreshToken;

    private LocalDateTime createdAt;

    public Member(String name, String email, String password,String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role="ROLE_USER";
        this.createdAt = LocalDateTime.now();
    }


    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public void updateRefreshToken(String refreshToken){
        this.refreshToken =refreshToken;
    }
}
