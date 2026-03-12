package com.example.demo.dto;

import lombok.Getter;

@Getter
public class CreateMemberRequest {
    private String name;
    private String email;
    private String password;
}
