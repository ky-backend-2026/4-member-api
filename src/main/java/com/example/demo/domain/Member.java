package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private LocalDateTime createdAt;

    public Member(String name, String email,String password){
        this.name = name;
        this.email=email;
        this.password=password;
        this.createdAt=LocalDateTime.now();
    }


    public void update(String name, String email){
        this.name=name;
        this.email=email;
    }
}
